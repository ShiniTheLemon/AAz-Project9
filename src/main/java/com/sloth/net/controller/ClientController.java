package com.sloth.net.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.sloth.net.entities.Posts;
import com.sloth.net.entities.Users;
import com.sloth.net.pojo.AuthRequest;
import com.sloth.net.pojo.AuthResponse;
import com.sloth.net.pojo.UsersJwt;

@Controller
@RequestMapping("/")
public class ClientController {
	@Autowired AuthController auth;
	private static final String url="http://localhost:3000/";

	@GetMapping("/")
	public String signInPage(){
		return "login";
	}
	@GetMapping("/mvc/signuppage")
	public String signUpPage() {
		return "SignUp";
	}
	@PostMapping("/mvc/signin")
	public String signIn( AuthRequest req,ModelMap modelMap) {
		RestTemplate temp= new RestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
	
		

		ResponseEntity<?>res=auth.login2(req);
			AuthResponse token = (AuthResponse) res.getBody();
			String accesstoken =token.getAccessAToken();
			
			

		
			if(res.getStatusCodeValue()==200) {
			Users user = new Users();
//			user.setEmail("test@gmail.com");
//			user.setPassword("65487744");
//			user.setRole("user");
//			user.setUser_id(1213);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			
			user.setEmail(currentPrincipalName);
			
			
			
			headers.setBearerAuth(accesstoken);
			HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);
			ResponseEntity<String> data=temp.exchange(url+"/api/posts/allPosts", HttpMethod.GET, httpEntity, String.class);
			System.out.println("The post api has been called "+data);
			
			modelMap.addAttribute("user", user);
			modelMap.addAttribute("token", accesstoken);
			return "Index";
		}else {
			modelMap.addAttribute("error", "Invalid user name or password");
			return "Login";
		}

		
	}
}
