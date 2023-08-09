package com.sloth.net.securityconfig;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sloth.net.entities.Users;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class Jwt {
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hours
	private static final Logger LOGGER=LoggerFactory.getLogger(Jwt.class);
	@Value("${app.jwt.secret}")
    private String key;
	
	//create token
	public String generateToken(Users user) {
		return Jwts.builder()
		.setSubject(String.format("%s,%s", user.getUser_id(),user.getEmail()))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
		.signWith(SignatureAlgorithm.HS256, key)
		.compact();
		

	}
	
	//check validity of token
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		}catch(ExpiredJwtException e) {
			LOGGER.error("Token has expired",e.getMessage());
		}catch(IllegalArgumentException e) {
			LOGGER.error("Token is  null, empty or has a blankspace", e.getMessage());
		}catch(MalformedJwtException e) {
			LOGGER.error("Token is invalid",e);
		}catch(UnsupportedJwtException e) {
			LOGGER.error("Token is not supported", e );
		}catch(SignatureException e) {
			LOGGER.error("Signature validation failed");
		}
		return false;
	}
	
	
	//will be used to get id and email from token
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}


	private Claims parseClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token)
				.getBody();
	}
	
}
