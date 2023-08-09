package com.sloth.net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sloth.net.entities.Users;
import com.sloth.net.repo.UserRepository;
@Service
public class UserServiceImp implements UsersService {
@Autowired
UserRepository userRepo;
@Autowired
PasswordEncoder passwordEncoder;
@Autowired
AuthenticationManager authManager;

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

}
