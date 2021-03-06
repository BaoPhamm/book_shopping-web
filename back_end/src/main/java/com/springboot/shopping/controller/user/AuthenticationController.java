package com.springboot.shopping.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.auth.AuthenticationRequest;
import com.springboot.shopping.dto.auth.AuthenticationResponse;
import com.springboot.shopping.service.AuthenticationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@Validated @RequestBody AuthenticationRequest request) {
		AuthenticationResponse authenticationResponse = authenticationService.login(request);
		return ResponseEntity.ok(authenticationResponse);
	}
}
