package com.springboot.shopping.controller.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.security.JwtProvider;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JwtProvider jwtProvider;

	@GetMapping("/info")
	public ResponseEntity<UserResponse> getUserInfo() {
		// Get username from SecurityContextHolder
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(userService.findUserByUsername(username));
	}

	@PutMapping("/edit/info")
	public ResponseEntity<UserResponse> updateUserInfo(@Valid @RequestBody UserRequest request,
			BindingResult bindingResult) {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(userService.updateProfile(username, request, bindingResult));
	}

	@PutMapping("/edit/password")
	public ResponseEntity<String> updateUserPassword(@Valid @RequestBody PasswordResetRequest passwordReset,
			BindingResult bindingResult) {

		// Get username from SecurityContextHolder
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		} else {
			return ResponseEntity.ok(userService.passwordReset(username, passwordReset));
		}
	}

	@GetMapping("/token/refresh")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		userService.refreshToken(request, response);
		return ResponseEntity.ok().build();
	}

}
