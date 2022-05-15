package com.springboot.shopping.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.service.impl.RegistrationServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class RegistrationController {

	private final RegistrationServiceImpl registrationService;

	@PostMapping
	public ResponseEntity<String> registration(@Validated @RequestBody RegistrationRequest registrationRequest) {
		String message = registrationService.registerUser(registrationRequest);
		return ResponseEntity.ok(message);
	}

}
