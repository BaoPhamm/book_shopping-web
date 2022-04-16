package com.springboot.shopping.service;

import com.springboot.shopping.model.User;

public interface AuthenticationService {

	String registerUser(User user, String password2);

}
