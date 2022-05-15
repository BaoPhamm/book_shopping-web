package com.springboot.shopping.service;

import com.springboot.shopping.dto.auth.AuthenticationRequest;
import com.springboot.shopping.dto.auth.AuthenticationResponse;

public interface AuthenticationService {
	AuthenticationResponse login(AuthenticationRequest request);

}
