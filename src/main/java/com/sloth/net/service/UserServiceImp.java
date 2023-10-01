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

import com.sloth.net.entities.Users;
import com.sloth.net.pojo.UsersJwt;
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

	@Override
	public Users signUp(Users user) {
		// TODO Auto-generated method stub
		user.setRole("user");
		//encodes password before saving user object
		user.setPassword(encodePassword(user.getPassword()));
		
		return userRepo.save(user);
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
