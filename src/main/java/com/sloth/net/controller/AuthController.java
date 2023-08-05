package com.sloth.net.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sloth.net.entities.Users;
import com.sloth.net.service.UsersService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UsersService service;
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@PostMapping("/login2")
	public ResponseEntity<Users> login2(@RequestBody Users user){
		return null;
	}
	
	@RequestMapping(value="/signUp",method=RequestMethod.POST)
	public ResponseEntity<Users> signUp(Users user){
		return new ResponseEntity<Users>(service.signUp(user),HttpStatus.CREATED);
	}
}
