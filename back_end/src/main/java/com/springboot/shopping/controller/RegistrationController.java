package com.springboot.shopping.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.mapper.AuthenticationMapper;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

	private final AuthenticationMapper authenticationMapper;

	public RegistrationController(AuthenticationMapper authenticationMapper) {
		super();
		this.authenticationMapper = authenticationMapper;
	}

	@PostMapping
	public ResponseEntity<String> registration(@Valid @RequestBody RegistrationRequest registrationRequest,
			BindingResult bindingResult) {
		return ResponseEntity.ok(authenticationMapper.registerUser(registrationRequest, bindingResult));
	}

}
