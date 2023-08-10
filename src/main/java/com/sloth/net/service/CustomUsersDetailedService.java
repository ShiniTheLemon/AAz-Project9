package com.sloth.net.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sloth.net.entities.Users;
import com.sloth.net.pojo.UsersJwt;
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
        List<GrantedAuthority> listAuthorities = new ArrayList<GrantedAuthority>();
        listAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new UsersJwt(user.getUser_id(),email, user.getPassword(), true, listAuthorities,user);
		
		
	}

	
}
