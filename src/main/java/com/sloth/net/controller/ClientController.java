package com.sloth.net.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sloth.net.entities.Posts;
import com.sloth.net.entities.User_info;
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
	public String signIn( AuthRequest req,ModelMap modelMap,HttpSession session) throws URISyntaxException {
		RestTemplate temp= new RestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
	
		try {
			//call login API
			 RequestEntity<?> request = RequestEntity
				     .post(new URI(url+"auth/api/signin"))
				     .accept(MediaType.APPLICATION_JSON)
				     .body(req);
				 ResponseEntity<AuthResponse> res = temp.exchange(request, AuthResponse.class);
				 
				 	//get token and create session 
					AuthResponse token =  (AuthResponse) res.getBody();
					String accesstoken =token.getAccessAToken();
					session.setAttribute("token", accesstoken);

					if(res.getStatusCodeValue()==200) {
					Users user = new Users();
				
					
					//get logged in user
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					String currentPrincipalName = authentication.getName();
					
					user.setEmail(currentPrincipalName);
					
					
					//set bearer authorization to header
					headers.setBearerAuth(accesstoken);
					HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);
					ResponseEntity<List> data=temp.exchange(url+"/api/posts/showAllUsers", HttpMethod.GET, httpEntity, List.class);
			
					List<User_info>userInfo=(List<User_info>) data.getBody();
					System.out.println("user info "+userInfo);
					
					modelMap.addAttribute("userInfo", userInfo);
					modelMap.addAttribute("user", user);
					modelMap.addAttribute("token", accesstoken);
					return "Index";
				}else {
					modelMap.addAttribute("error", "Invalid user name or password");
					return "Login";
				}
		}catch(HttpClientErrorException e) {
			e.printStackTrace();
			return "Login";
		}


		
	}
	@GetMapping("/mvc/global/profiles")
	public String globalProfiles(int user_id) {
		return null;
	}
	@GetMapping("/mvc/global/topics")
	public String globalTopics() {
		return null;
	}
	@GetMapping("/mvc/specific/topic")
	public String specificTopic(int user_id) {
		return null;
	}
	@GetMapping("/mvc/single/topic")
	public String SingleTopic(int post_id) {
		return null;
	}
	
	
	@PostMapping("/mvc/likedislike/{choice}")
	public String likeDislike(@PathVariable int choice){
		return null;
	}
	
	@PostMapping("/mvc/update/info")
	public String updateInfo(User_info info) {
		return null;
	}
	@PostMapping("/mvc/chat")
	public String chat() {
		return null;
	}
}
