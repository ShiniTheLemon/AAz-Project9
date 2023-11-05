package com.sloth.net.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sloth.net.entities.User_info;
import com.sloth.net.entities.Users;
import com.sloth.net.pojo.UsersJwt;
import com.sloth.net.repo.UserInfoRepository;
import com.sloth.net.repo.UserRepository;
import com.sloth.net.securityconfig.Jwt;
@Service
public class UserServiceImp implements UsersService {
@Autowired
UserRepository userRepo;
@Autowired
PasswordEncoder passwordEncoder;
@Autowired
AuthenticationManager authManager;
@Autowired
Jwt jwt;
@Autowired
UserInfoRepository infoRepo;


///call repo to get user id
	@Override
	public Users signUp(Users user) {
		// TODO Auto-generated method stub
		user.setRole("user");
		//encodes password before saving user object
		user.setPassword(encodePassword(user.getPassword()));
		
		Users userObj= userRepo.save(user);
		System.out.println("USER OBJECT CONTENTS "+userObj);
		String email=userObj.getEmail();
		
		int userid=userRepo.findUsersByEmail(email).get().getUser_id();
		System.out.println("USER ID OBTAINTED "+userid);
		
		
		User_info infoObj=new User_info();
		infoObj.setUser_name(email);
		infoObj.setUserid(userid);
		infoRepo.save(infoObj);
		return userObj;
	}
	private String encodePassword(String Password) {
		return passwordEncoder.encode(Password);
	}
	@Override
	public void login(Users user) {
		// TODO Auto-generated method stub
		Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail()
				,user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		User_info info=new User_info();
		
		info.setUserid(user.getUser_id());
		info.setUser_name(user.getEmail());
		infoRepo.save(info);
	}
	@Override
	public  UsersJwt loggedInUser(String token) {
		UsersJwt user=new UsersJwt();
		String[] jwtSubject=jwt.getSubject(token).split(",");
		//UserDetails user=userDetails.loadUserByUsername(jwtSubject[1]);
		user.setUser_id(Integer.parseInt(jwtSubject[0]));
		user.setEmail(jwtSubject[1]);
		return user;
	}

}
