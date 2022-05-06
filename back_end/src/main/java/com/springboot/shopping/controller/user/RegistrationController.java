package com.springboot.shopping.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
	public ResponseEntity<String> registration(@Validated @RequestBody RegistrationRequest registrationRequest) {
		String message = authenticationService.registerUser(registrationRequest);
		return ResponseEntity.ok(message);
	}

}
