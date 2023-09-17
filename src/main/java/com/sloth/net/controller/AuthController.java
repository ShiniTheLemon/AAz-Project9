package com.sloth.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sloth.net.entities.Users;
import com.sloth.net.pojo.AuthRequest;
import com.sloth.net.pojo.AuthResponse;
import com.sloth.net.securityconfig.Jwt;
import com.sloth.net.service.UsersService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UsersService service;
	@Autowired
	Jwt jwt;
	@Autowired
	AuthenticationManager authManager;

	//checks if user is authenticated then generates token
	@PostMapping("/api/signin")
	public ResponseEntity<?> login2( AuthRequest req){
		try {
			Authentication auth=authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							req.getEmail(),req.getPassword()));
			
			//Users user =(Users)auth.getPrincipal();
			//String accessToken=jwt.generateToken(user);
			SecurityContextHolder.getContext().setAuthentication(auth);
			AuthResponse res=new AuthResponse(req.getEmail(),jwt.generateToken(auth));
		
			return ResponseEntity.ok().body(res);
			
		}catch(BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	
	@RequestMapping(value="/api/signUp",method=RequestMethod.POST)
	public ResponseEntity<Users> signUp(Users user){
		return new ResponseEntity<Users>(service.signUp(user),HttpStatus.CREATED);
	}
}
