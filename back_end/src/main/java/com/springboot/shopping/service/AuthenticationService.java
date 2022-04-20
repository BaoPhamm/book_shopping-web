package com.springboot.shopping.service;

import java.util.Map;

import com.springboot.shopping.model.UserEntity;

public interface AuthenticationService {

	String registerUser(UserEntity user, String password2);
	
	Map<String, String> login(String username, String password);

}
