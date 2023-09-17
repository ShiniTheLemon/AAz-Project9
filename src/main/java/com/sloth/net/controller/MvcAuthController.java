package com.sloth.net.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.sloth.net.entities.Users;
import com.sloth.net.pojo.AuthRequest;
import com.sloth.net.pojo.UsersJwt;

@Controller
@RequestMapping("/")
public class MvcAuthController {
	@Autowired AuthController auth;


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
		
	
		ResponseEntity<?>res=auth.login2(req);
		if(res.getStatusCodeValue()==200) {
			Users user = new Users();
//			user.setEmail("test@gmail.com");
//			user.setPassword("65487744");
//			user.setRole("user");
//			user.setUser_id(1213);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			
			user.setEmail(currentPrincipalName);
			
			modelMap.addAttribute("user", user);
			return "Index";
		}else {
			modelMap.addAttribute("error", "Invalid user name or password");
			return "Login";
		}

		
	}
}
