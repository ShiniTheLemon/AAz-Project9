package com.sloth.net.service;

import org.springframework.stereotype.Service;

import com.sloth.net.entities.Users;

@Service
public interface UsersService {
	Users signUp(Users user);
}
