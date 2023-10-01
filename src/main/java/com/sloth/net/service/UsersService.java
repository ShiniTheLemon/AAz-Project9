package com.sloth.net.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sloth.net.entities.Users;
import com.sloth.net.pojo.UsersJwt;

@Service
public interface UsersService {
	Users signUp(Users user);
	void login(Users user);
	UsersJwt loggedInUser(String token);
	
}
