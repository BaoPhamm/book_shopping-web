package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

	private final UserService userService;

	// Get user by ID
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long userId) {
		UserResponse userResponse = userService.findUserById(userId);
		return ResponseEntity.ok(userResponse);
	}

	// Get All users
	@GetMapping()
	public ResponseEntity<List<UserResponse>> getAllUsers(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "25") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		List<UserResponse> allUsers = userService.findAllUsers(pageable);
		return ResponseEntity.ok(allUsers);
	}

	// Delete an existing user by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
		String message = userService.deleteUser(userId);
		return ResponseEntity.ok(message);
	}

	// Block an existing user by ID
	@PutMapping("/block")
	public ResponseEntity<String> blockUser(@RequestBody Long userId) {
		String message = userService.blockUser(userId);
		return ResponseEntity.ok(message);
	}

	// Unblock an existing user by ID
	@PutMapping("/unblock")
	public ResponseEntity<String> unBlockUser(@RequestBody Long UserId) {
		String message = userService.unBlockUser(UserId);
		return ResponseEntity.ok(message);
	}

	// get total users
	@GetMapping("/total")
	public ResponseEntity<Long> getTotalUsers() {
		Long totalUsers = userService.getTotalUsers();
		return ResponseEntity.ok(totalUsers);
	}
}
