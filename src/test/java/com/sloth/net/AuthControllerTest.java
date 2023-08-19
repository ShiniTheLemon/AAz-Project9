package com.sloth.net;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sloth.net.controller.AuthController;
import com.sloth.net.entities.Users;
import com.sloth.net.service.UsersService;

@WebMvcTest(AuthController.class)

public class AuthControllerTest {
    @Autowired private MockMvc mockMvc;
    
    @Autowired private ObjectMapper objectMapper;
     
    @MockBean private UsersService service;

    
//    @Before
//    public void setUp() {
// 
//
//    }
    
	@Test
	public void testAddShouldReturn201Created() throws Exception {
	   
		Users newUser=new Users("email@gmail.com","123");
	 

		Mockito.when(service.signUp(newUser)).thenReturn(newUser);
	    String requestBody = objectMapper.writeValueAsString(newUser);
	  
	 
	    mockMvc.perform(post("/auth/signUp").contentType("application/json")
	            .content(requestBody))
	            .andExpect(status().isCreated())
	            .andDo(print())
	    ;
	 
	}
}
