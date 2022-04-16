package com.springboot.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.role.RoleRequest;
import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.AddRoleToUserForm;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserMapper userMapper;

	// Get user by ID
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long UserId) {
		return ResponseEntity.ok(userMapper.findUserById(UserId));
	}

	// Get All users
	@GetMapping()
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(userMapper.findAllUsers());
	}

	// Update an existing user
	@PutMapping("/update/{id}")
	public ResponseEntity<UserResponse> updateProfileUser(@PathVariable("id") Long UserId,
			@RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userMapper.updateProfileUser(UserId, userRequest));
	}

	// Delete an existing user by ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<List<UserResponse>> deleteUser(@PathVariable("id") Long UserId) {
		return ResponseEntity.ok(userMapper.deleteUser(UserId));
	}

	@PostMapping("/create-role")
	public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
		return ResponseEntity.ok(userMapper.createRole(roleRequest));
	}

	@PostMapping("/add-role-to-user")
	public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserForm addRoleToUserForm) {
		userMapper.addRoleToUser(addRoleToUserForm.getUsername(), addRoleToUserForm.getRolename());
		return ResponseEntity.ok().build();
	}

}
