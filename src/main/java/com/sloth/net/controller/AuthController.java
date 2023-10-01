package com.sloth.net.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sloth.net.entities.Users;
import com.sloth.net.pojo.AuthRequest;
import com.sloth.net.pojo.AuthResponse;
import com.sloth.net.pojo.UsersJwt;
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
<<<<<<< Updated upstream

=======
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "Index";
	}
	
	
>>>>>>> Stashed changes
	//checks if user is authenticated then generates token
	@PostMapping("/api/signin")
	public ResponseEntity<?> login2(@RequestBody AuthRequest req){
		System.out.println("user login info "+ req);
		try {
			System.out.println("before method authentication method ");
			
			Authentication auth=authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							req.getEmail(),req.getPassword()));
			
			System.out.println("after method authentication method ");
			
			//Users user =(Users)auth.getPrincipal();
			//String accessToken=jwt.generateToken(user);
			
			System.out.println("before security context is set ");
			
			SecurityContextHolder.getContext().setAuthentication(auth);
			
			System.out.println("after security context is set ");
			
			AuthResponse res=new AuthResponse(req.getEmail(),jwt.generateToken(auth));
			
			System.out.println("after response object ");
			
			System.out.println("end of api method "+res);
			return  new ResponseEntity<AuthResponse>(res,HttpStatus.OK);
			
		}catch(BadCredentialsException e) {
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@RequestMapping(value="/api/signUp",method=RequestMethod.POST)
	public ResponseEntity<Users> signUp(@RequestBody Users user){
		System.out.println("sign up info "+ user);
		return new ResponseEntity<Users>(service.signUp(user),HttpStatus.CREATED);
	}
	@GetMapping("/api/loggedInUser/{accesstoken}")
	public ResponseEntity<UsersJwt>loggedInUser(@PathVariable String accesstoken){
		System.out.println("api method has been called "+ service.loggedInUser(accesstoken));
		return new ResponseEntity<UsersJwt>(service.loggedInUser(accesstoken),HttpStatus.OK);
	}
}
