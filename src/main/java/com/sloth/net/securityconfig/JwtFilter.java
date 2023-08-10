package com.sloth.net.securityconfig;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sloth.net.entities.Users;
import com.sloth.net.pojo.UsersJwt;

@Component
public class JwtFilter extends OncePerRequestFilter{
@Autowired 
private Jwt jwt;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	if(!hasAuthorizationBearerToken(request)) {
		filterChain.doFilter(request, response);
		return;
	}	
	String token=getAccessToken(request);
	
	if(!jwt.validateAccessToken(token)) {
		filterChain.doFilter(request, response);
		return;
	}
	
	setAuthenticationContext(token,request);
	filterChain.doFilter(request, response);
	}

	
	//updates the security context
	private void setAuthenticationContext(String token, HttpServletRequest request) {
		// TODO Auto-generated method stub
		UserDetails userDetails=getUserDetails(token);
		UsernamePasswordAuthenticationToken auth=
				new UsernamePasswordAuthenticationToken(userDetails,null,null);
		
		auth.setDetails(
				new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(auth);
		
	}

	//gets the extracts the token
	private String getAccessToken(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String header=request.getHeader("Authorization");
		String token=header.split(" ")[1].trim();
		return token;
	}

	//checks if request has an authorization bearer token
	private boolean hasAuthorizationBearerToken(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String header=request.getHeader("Authorization");
		if(ObjectUtils.isEmpty(header)|| !header.startsWith("Bearer")) {
			return false;
		}
		return true;
	}
	
	//extracts the user id and email from the token
	private UserDetails getUserDetails(String token) {
		UsersJwt user=new UsersJwt();
		String[] jwtSubject=jwt.getSubject(token).split(",");
		//UserDetails user=userDetails.loadUserByUsername(jwtSubject[1]);
		user.setUser_id(Integer.parseInt(jwtSubject[0]));
		user.setEmail(jwtSubject[1]);
		return user;
	}
}
