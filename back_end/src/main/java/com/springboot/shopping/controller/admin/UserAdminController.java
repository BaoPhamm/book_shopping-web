package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

	private final UserService userService;

	@Value("${jwt.secret}")
	private String secretKey;

	// Get user by ID
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long UserId) {
		UserResponse userResponse = userService.findUserById(UserId);
		return ResponseEntity.ok(userResponse);
	}

	// Get All users
	@GetMapping()
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<UserResponse> allUsers = userService.findAllUsers();
		return ResponseEntity.ok(allUsers);
	}

	// Delete an existing user by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<List<UserResponse>> deleteUser(@PathVariable("id") Long UserId) {
		String adminUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserResponse> newUserList = userService.deleteUser(UserId, adminUsername);
		return ResponseEntity.ok(newUserList);
	}

	@PostMapping("/role/add-to-user")
	public ResponseEntity<String> addRoleToUser(@RequestBody AddRoleToUserForm addRoleToUserForm) {
		String message = userService.addRoleToUser(addRoleToUserForm.getUsername(), addRoleToUserForm.getRolename());
		return ResponseEntity.ok(message);
	}

	@PostMapping("/role/remove-from-user")
	public ResponseEntity<String> removeRoleFromUser(@RequestBody RemoveRoleFromUserForm removeRoleFromUserForm) {
		String message = userService.removeRoleFromUser(removeRoleFromUserForm.getUsername(),
				removeRoleFromUserForm.getRolename());
		return ResponseEntity.ok(message);
	}
}
