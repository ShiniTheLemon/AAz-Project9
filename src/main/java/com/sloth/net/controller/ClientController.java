package com.sloth.net.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.el.stream.Optional;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sloth.net.entities.Comments;
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
	private static final String authUrl=url+"auth/api/";
	private static final String apiUrl=url+"api/posts/";
	private  RestTemplate temp=new RestTemplate();

	@GetMapping("/")
	public String signInPage(){
		return "login";
	}
	@GetMapping("/mvc/signuppage")
	public String signUpPage(ModelMap modelMap) {
		modelMap.addAttribute("error", "SIGN UP");
		return "SignUp";
	}
	
	@PostMapping("/mvc/signUp")
	public String signUp(Users user,ModelMap modelMap) {
		try {
			Users res = temp.postForObject(authUrl+"signUp", user,Users.class);
			return signInPage();
		}catch(Exception e) {
			modelMap.addAttribute("error", "Right! Something Went Wrong, i don't know what..lol");
			return "SignUp";
		}
		
		
	}
	@PostMapping("/mvc/signin")
	public String signIn( AuthRequest req,ModelMap modelMap,HttpSession session) throws URISyntaxException {

		System.out.println("client method is running ");
	
		try {
			//call login API
			 RequestEntity<?> request = RequestEntity
				     .post(new URI(authUrl+"signin"))
				     .accept(MediaType.APPLICATION_JSON)
				     .body(req);
			 System.out.println("first client api has been called  request value"+request);
			 
				 ResponseEntity<AuthResponse> res = temp.exchange(request, AuthResponse.class);
				 
				 System.out.println("first client api has been called  "+res);

					if(res.getStatusCodeValue()==200) {
						 System.out.println("first condition is true "+res.getStatusCodeValue());	
			
					 //get web token and create session 
					AuthResponse token =  (AuthResponse) res.getBody();
					String accesstoken =token.getAccessAToken();
					session.setAttribute("webtoken", accesstoken);
					
					
					//get logged in user
					ResponseEntity<UsersJwt>singleUserData=temp.getForEntity(authUrl+"loggedInUser/"+accesstoken, UsersJwt.class);
					 
					System.out.println("logged in user info  "+singleUserData.getBody());	
					
					UsersJwt loggedInUser = (UsersJwt) singleUserData.getBody() ;
					
					//set bearer authorization to header
					//access show all users API
					HttpHeaders headers=new HttpHeaders();
					headers.set("Accept", "application/json");
					headers.setBearerAuth(accesstoken);
					HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);
					ResponseEntity<List> allUsersdata=temp.exchange(apiUrl+"showAllUsers", HttpMethod.GET, httpEntity, List.class);
			
					List<User_info>userInfo=(List<User_info>) allUsersdata.getBody();
					System.out.println("user info "+userInfo);
					
					modelMap.addAttribute("userInfo", userInfo);
					modelMap.addAttribute("user", loggedInUser.getEmail());
					modelMap.addAttribute("token", accesstoken);
					return "Index";
				}else {
					modelMap.addAttribute("error", "Invalid user name or password");
					return "Login";
				}
		}catch(HttpClientErrorException e) {
			modelMap.addAttribute("error", "Invalid credentials authentication failure");
			System.out.println("none of the conditions are  true ");	
			e.printStackTrace();
		
			return "Login";
		}


		
	}
	
	////POSTS
	
	@GetMapping("/mvc/global/profiles")
	public String globalProfiles(HttpSession session,ModelMap modelMap) {
		//int user_id=12505;
		String accesstoken=(String)session.getAttribute("webtoken");
		
		ResponseEntity<UsersJwt>singleUserData=temp.getForEntity(authUrl+"loggedInUser/"+accesstoken, UsersJwt.class);
		int user_id = (Integer) singleUserData.getBody().getUser_id() ;
		

		System.out.println("global profiles user id "+user_id);
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		
		HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
		ResponseEntity<User_info> res= temp.exchange(apiUrl+"showUser/"+user_id, HttpMethod.GET, httpEntity, User_info.class);
		
		User_info userData=res.getBody();
		
		modelMap.addAttribute("userData", userData);
		System.out.println("user profile data "+ userData);
		
		return "Profiles";
	}
	@GetMapping("/mvc/global/topics")
	public String globalTopics(HttpSession session,ModelMap modelMap) {
		
		String accesstoken=(String)session.getAttribute("webtoken");
		
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
		ResponseEntity<List> res= temp.exchange(apiUrl+"allPosts", HttpMethod.GET, httpEntity, List.class);
		List<Posts>topics=res.getBody();
		
		modelMap.addAttribute("topics", topics);
		
		
		return "Topics";
	}
	@GetMapping("/mvc/specific/topics")
	public String specificTopic(@RequestParam int user_id,HttpSession session,ModelMap modelMap) {
		String accesstoken=(String)session.getAttribute("webtoken");
		
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
		ResponseEntity<List> res= temp.exchange(apiUrl+"userPosts/"+user_id, HttpMethod.GET, httpEntity, List.class);
		List<Posts>topics=res.getBody();
		
		
		System.out.println(" SPECIFIC TOPIC DATA "+topics);
		modelMap.addAttribute("topics", topics);
		
		
		return "Topics";
	}
	@GetMapping("/mvc/my/topic")
	public String MyTopic(HttpSession session,ModelMap modelMap) {
		String accesstoken=(String)session.getAttribute("webtoken");
		
		ResponseEntity<UsersJwt>singleUserData=temp.getForEntity(authUrl+"loggedInUser/"+accesstoken, UsersJwt.class);
		int user_id=singleUserData.getBody().getUser_id();
		
		
		return specificTopic(user_id,session,modelMap);
	}
	
	//needs  work
	//like seriously this is going to brake
	@PostMapping("/mvc/create/topic")
	public String createPost(HttpSession session, ModelMap modelMap,String topic,String post) throws URISyntaxException {
		String accesstoken=(String)session.getAttribute("webtoken");
		
		ResponseEntity<UsersJwt>singleUserData=temp.getForEntity(authUrl+"loggedInUser/"+accesstoken, UsersJwt.class);
		int user_id=singleUserData.getBody().getUser_id();
		
		
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
		
		
		 	
		ResponseEntity<Posts> res =temp.exchange(apiUrl+"createPost/"+user_id+"/"+topic+"/"+post, HttpMethod.GET, httpEntity, Posts.class);
		Posts topics = res.getBody();
		modelMap.addAttribute("topics", topics);
		
		return "Topics";
	}
	@PostMapping("/mvc/delete/topic")
	public String deleteTopic(int post_id,HttpSession session,ModelMap modelMap) {
		String accesstoken=(String)session.getAttribute("webtoken");
		
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
		ResponseEntity<Posts> res= temp.exchange(apiUrl+"deletePost/"+post_id, HttpMethod.POST, httpEntity, Posts.class);
		Posts topics=res.getBody();
		
		
		ResponseEntity<UsersJwt>singleUserData=temp.getForEntity(authUrl+"loggedInUser/"+accesstoken, UsersJwt.class);
		int user_id=singleUserData.getBody().getUser_id();
			
		return specificTopic( user_id, session, modelMap);
	}
	@PostMapping("/mvc/edit/topic")
	public String editTopic(int post_id,String post,HttpSession session,ModelMap modelMap) {
		String accesstoken=(String)session.getAttribute("webtoken");
		
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
		ResponseEntity<Posts> res= temp.exchange(apiUrl+"editPost/"+post_id+"/"+post, HttpMethod.POST, httpEntity, Posts.class);
		Posts topics=res.getBody();
		

		modelMap.addAttribute("topics", topics);
		return "Topics";
	}
	
	
	@GetMapping("/mvc/likedislike")
	public String likeDislike(@RequestParam int choice,@RequestParam int id,@RequestParam int x,HttpSession session,ModelMap modelMap){
		String accesstoken=(String)session.getAttribute("webtoken");
		
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
		ResponseEntity<?>res = null;
		int commentOrPost=0;
		switch (choice){
		 case 0:
			res= temp.exchange(apiUrl+"likePost/"+id, HttpMethod.POST, httpEntity, Posts.class);
			commentOrPost=1;
			break;
		 case 1:
			 res= temp.exchange(apiUrl+"dislikePost/"+id, HttpMethod.POST, httpEntity, Posts.class);
			 commentOrPost=1;
			 break;
		 case 2:
			 res= temp.exchange(apiUrl+"likeComment/"+id, HttpMethod.POST, httpEntity, List.class);
			 commentOrPost=2;
			 break;
		 case 3:
			 res= temp.exchange(apiUrl+"dislikeComment/"+id, HttpMethod.POST, httpEntity, List.class);
			 commentOrPost=2;
			 break; 
		}
		
		if(commentOrPost==1) {
			Posts topics=(Posts) res.getBody();
			modelMap.addAttribute("topics", topics);
			return "Topics";
		}else {
			List<Comments> innit=(List) res.getBody();
			ResponseEntity<Posts> res2= temp.exchange(apiUrl+"singlePosts/"+x, HttpMethod.GET, httpEntity, Posts.class);
			Posts topics=res2.getBody();
			modelMap.addAttribute("comments", innit);
			modelMap.addAttribute("topics", topics);	
			return "Comments";
			
		}

	}
	
	//COMMENTS
	
	@GetMapping("/mvc/all/comments")
	public String allComments(@RequestParam int post_id,HttpSession session,ModelMap modelMap) {
		String accesstoken=(String)session.getAttribute("webtoken");
		
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
		ResponseEntity<Posts> res= temp.exchange(apiUrl+"singlePosts/"+post_id, HttpMethod.GET, httpEntity, Posts.class);
		ResponseEntity<List>res2=temp.exchange(apiUrl+"allComments/"+post_id, HttpMethod.GET, httpEntity, List.class);
		Posts topics=res.getBody();
		List <Comments>commentList=res2.getBody();
		
		modelMap.addAttribute("topics", topics);
		modelMap.addAttribute("comments", commentList);
		
		
		return "Comments";
	}
	@GetMapping("/mvc/comments")
	public String Comments(@RequestParam Integer id,@RequestParam String body, HttpSession session,ModelMap modelMap) {
		String accesstoken=(String)session.getAttribute("webtoken");
		
		ResponseEntity<UsersJwt>singleUserData=temp.getForEntity(authUrl+"loggedInUser/"+accesstoken, UsersJwt.class);
		int user_id=singleUserData.getBody().getUser_id();
		
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
	
		ResponseEntity<List>res2=temp.exchange(apiUrl+"comment/"+user_id+"/"+body+"/"+id, HttpMethod.POST, httpEntity, List.class);
		ResponseEntity<Posts> res= temp.exchange(apiUrl+"singlePosts/"+id, HttpMethod.GET, httpEntity, Posts.class);
		Posts topics=res.getBody();
		List <Comments>commentList=res2.getBody();
		
		modelMap.addAttribute("topics", topics);
		modelMap.addAttribute("comments", commentList);
		
		
		return "Comments";
	}
	
	
	@PostMapping("/mvc/update/info")
	public String updateInfo( User_info info,HttpSession session,ModelMap modelMap) {
		String accesstoken=(String)session.getAttribute("webtoken");
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity <User_info>httpEntity=new HttpEntity<>(info,headers);
		
		ResponseEntity<User_info>res=temp.exchange(apiUrl+"addInfo", HttpMethod.POST, httpEntity, User_info.class);
		
		User_info userData=res.getBody();
		modelMap.addAttribute("userData", userData);
		System.out.println("user profile data "+ userData);
		
		return "Profiles";
	}
	
	@GetMapping("/mvc/all/profiles")
	public String allProfiles(HttpSession session,ModelMap modelMap) {
		String accesstoken=(String)session.getAttribute("webtoken");
		HttpHeaders headers=new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setBearerAuth(accesstoken);
		HttpEntity <String>httpEntity=new HttpEntity<>("",headers);
		
		ResponseEntity<List> res=temp.exchange(apiUrl+"showAllUsers", HttpMethod.GET, httpEntity, List.class);
		List<User_info>userData=res.getBody();
		modelMap.addAttribute("userInfo", userData);
		
		return "Index";
	}
	@PostMapping("/mvc/vl")
	public String chat() {
		return "NotYet";
	}
}
