package com.sloth.net.service;


import java.util.Collection;
import java.util.Collections;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
		

		
        return new Users(user.getUser_id(),email,user.getPassword(), null);
		
		
	}
    private Collection<? extends GrantedAuthority> getAuthorities(String user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user));
    }
	
}
