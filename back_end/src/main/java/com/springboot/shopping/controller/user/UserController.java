package com.springboot.shopping.controller.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/info")
	public ResponseEntity<UserResponse> getUserInfo() {
		// Get username from SecurityContextHolder
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserResponse user = userService.findUserByUsername(username);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/info")
	public ResponseEntity<UserResponse> updateUserInfo(@Validated @RequestBody UserRequest request) {
		// Get username from SecurityContextHolder
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserResponse updatedUser = userService.updateProfile(username, request);
		return ResponseEntity.ok(updatedUser);
	}

	@PutMapping("/password")
	public ResponseEntity<String> updateUserPassword(@Validated @RequestBody PasswordResetRequest passwordReset) {
		// Get username from SecurityContextHolder
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String message = userService.passwordReset(username, passwordReset);
		return ResponseEntity.ok(message);

	}

	@GetMapping("/token/refresh")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		userService.refreshToken(request, response);
		return ResponseEntity.ok().build();
	}

}
