package com.springboot.shopping.service;

import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.dto.auth.AuthenticationRequest;
import com.springboot.shopping.dto.auth.AuthenticationResponse;

public interface AuthenticationService {

	String registerUser(RegistrationRequest registrationRequest, BindingResult bindingResult);

	AuthenticationResponse login(AuthenticationRequest request);

}
