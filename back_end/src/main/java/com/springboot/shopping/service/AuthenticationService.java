package com.springboot.shopping.service;

import java.util.Map;

import com.springboot.shopping.model.User;

public interface AuthenticationService {

	String registerUser(User user, String password2);
	
	Map<String, String> login(String username, String password);

}
