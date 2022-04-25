package com.springboot.shopping.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class RegistrationController {

	private final AuthenticationService authenticationService;

	@PostMapping
	public ResponseEntity<String> registration(@Valid @RequestBody RegistrationRequest registrationRequest,
			BindingResult bindingResult) {
		return ResponseEntity.ok(authenticationService.registerUser(registrationRequest, bindingResult));
	}

}
