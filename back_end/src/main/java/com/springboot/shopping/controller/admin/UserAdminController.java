package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.user.AddRoleToUserForm;
import com.springboot.shopping.dto.user.RemoveRoleFromUserForm;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class UserAdminController {

	private final UserService userService;

	@Value("${jwt.secret}")
	private String secretKey;

	// Get user by ID
	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long UserId) {
		UserResponse userResponse = userService.findUserById(UserId);
		return ResponseEntity.ok(userResponse);
	}

	// Get All users
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<UserResponse> allUsers = userService.findAllUsers();
		return ResponseEntity.ok(allUsers);
	}

	// Delete an existing user by ID
	@DeleteMapping("/users/delete/{id}")
	public ResponseEntity<List<UserResponse>> deleteUser(@PathVariable("id") Long UserId) {
		List<UserResponse> newUserList = userService.deleteUser(UserId);
		return ResponseEntity.ok(newUserList);
	}

	@PostMapping("/users/role/add-to-user")
	public ResponseEntity<String> addRoleToUser(@RequestBody AddRoleToUserForm addRoleToUserForm) {
		String message = userService.addRoleToUser(addRoleToUserForm.getUsername(), addRoleToUserForm.getRolename());
		return ResponseEntity.ok(message);
	}

	@PostMapping("/users/role/remove-from-user")
	public ResponseEntity<String> removeRoleFromUser(@RequestBody RemoveRoleFromUserForm removeRoleFromUserForm) {
		String message = userService.removeRoleFromUser(removeRoleFromUserForm.getUsername(),
				removeRoleFromUserForm.getRolename());
		return ResponseEntity.ok(message);
	}
}
