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

import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.AddRoleToUserForm;
import com.springboot.shopping.dto.user.RemoveRoleFromUserForm;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.service.RoleService;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class UserAdminController {

	private final UserService userService;
	private final RoleService roleService;

	@Value("${jwt.secret}")
	private String secretKey;

	// Get user by ID
	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long UserId) {
		return ResponseEntity.ok(userService.findUserById(UserId));
	}

	// Get All users
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(userService.findAllUsers());
	}

	// Delete an existing user by ID
	@DeleteMapping("/users/delete/{id}")
	public ResponseEntity<List<UserResponse>> deleteUser(@PathVariable("id") Long UserId) {
		return ResponseEntity.ok(userService.deleteUser(UserId));
	}

	@PostMapping("/role/create")
	public ResponseEntity<RoleResponse> createRole(@RequestBody String roleName) {
		return ResponseEntity.ok(roleService.createRole(roleName));
	}

	@PostMapping("/users/role/add-to-user")
	public ResponseEntity<String> addRoleToUser(@RequestBody AddRoleToUserForm addRoleToUserForm) {
		userService.addRoleToUser(addRoleToUserForm.getUsername(), addRoleToUserForm.getRolename());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/users/role/remove-from-user")
	public ResponseEntity<String> removeRoleFromUser(@RequestBody RemoveRoleFromUserForm removeRoleFromUserForm) {
		userService.removeRoleFromUser(removeRoleFromUserForm.getUsername(), removeRoleFromUserForm.getRolename());
		return ResponseEntity.ok().build();
	}
}
