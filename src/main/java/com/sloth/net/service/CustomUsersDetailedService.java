package com.sloth.net.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sloth.net.entities.Users;
import com.sloth.net.repo.UserRepository;

@Service
public class CustomUsersDetailedService implements UserDetailsService {
	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user= userRepo.findUsersByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException(email+" Is not a valid email"));
//		if(user==null) {
//			throw new UsernameNotFoundException("User does not exist");
//		}
		
		
		// why do i need to generate a list of the roles?
		List<String> roles= Arrays.asList(user.getRole());
		UserDetails userDetails=
				org.springframework.security.core.userdetails.User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole())
				.build();
		return userDetails;
		
	}
	
	
}
