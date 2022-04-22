package com.springboot.shopping.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.order.OrderRequest;
import com.springboot.shopping.dto.order.OrderResponse;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.mapper.OrderMapper;
import com.springboot.shopping.mapper.UserMapper;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.security.JwtProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UserMapper userMapper;
	private final OrderMapper orderMapper;
	private final JwtProvider jwtProvider;

	@GetMapping("/info")
	public ResponseEntity<UserResponse> getUserInfo() {
		// Get username from SecurityContextHolder
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(userMapper.findUserByUsername(username));
	}

	@PutMapping("/edit/info")
	public ResponseEntity<UserResponse> updateUserInfo(@Valid @RequestBody UserRequest request,
			BindingResult bindingResult) {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(userMapper.updateProfile(username, request, bindingResult));
	}

	@PutMapping("/edit/password")
	public ResponseEntity<String> updateUserPassword(@Valid @RequestBody PasswordResetRequest passwordReset,
			BindingResult bindingResult) {

		// Get username from SecurityContextHolder
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		} else {
			return ResponseEntity.ok(userMapper.passwordReset(username, passwordReset));
		}
	}

	@PostMapping("/order")
	public ResponseEntity<OrderResponse> postOrder(@Valid @RequestBody OrderRequest order,
			BindingResult bindingResult) {
		return ResponseEntity.ok(orderMapper.postOrder(order, bindingResult));
	}

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				// Get username from header
				String username = jwtProvider.getUsername(authorizationHeader);
				// Get User entity from DB
				UserEntity user = userMapper.findUserByUsernameReturnObject(username);
				// Get User roles
				List<String> userRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
				// Create new accessToken
				String accessToken = jwtProvider.createToken(username, userRoles);

				Map<String, String> tokens = new HashMap<String, String>();
				tokens.put("accessToken", accessToken);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				response.setHeader("Header", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());

				Map<String, String> errors = new HashMap<String, String>();
				errors.put("Error_message", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), errors);
			}

		} else {
			throw new RuntimeException("Refesh token is missing!");
		}
	}
	
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getUserOrders() {
    	String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(orderMapper.findOrderByUsername(username));
    }
    
    @PostMapping("/order")
    public ResponseEntity<OrderResponse> postOrder1(@Valid @RequestBody OrderRequest order, BindingResult bindingResult) {
        return ResponseEntity.ok(orderMapper.postOrder(order, bindingResult));
    }

}
