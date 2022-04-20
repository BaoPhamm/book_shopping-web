package com.springboot.shopping.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.auth.AuthenticationRequest;
import com.springboot.shopping.dto.auth.AuthenticationResponse;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.mapper.AuthenticationMapper;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationMapper authenticationMapper;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authenticationMapper.login(request));
	}
}
