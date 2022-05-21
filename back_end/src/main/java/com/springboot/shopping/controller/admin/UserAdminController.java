package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<List<UserResponse>> getAllUsers(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "20") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		List<UserResponse> allUsers = userService.findAllUsers(pageable);
		return ResponseEntity.ok(allUsers);
	}

	// Delete an existing user by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long UserId) {
		String adminUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String message = userService.deleteUser(UserId, adminUsername);
		return ResponseEntity.ok(message);
	}

	// Block an existing user by ID
	@PutMapping("/block")
	public ResponseEntity<String> blockUser(@RequestBody Long UserId) {
		String adminUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String message = userService.blockUser(UserId, adminUsername);
		return ResponseEntity.ok(message);
	}

	// Unblock an existing user by ID
	@PutMapping("/unblock")
	public ResponseEntity<String> unBlockUser(@RequestBody Long UserId) {
		String message = userService.unBlockUser(UserId);
		return ResponseEntity.ok(message);
	}

	@PostMapping("/role/add-to-user")
	public ResponseEntity<String> addRolesToUser(@RequestBody AddRoleToUserForm addRoleToUserForm) {
		String message = userService.addRolesToUser(addRoleToUserForm.getUserId(), addRoleToUserForm.getRolesId());
		return ResponseEntity.ok(message);
	}

	@PostMapping("/role/remove-from-user")
	public ResponseEntity<String> removeRolesFromUser(@RequestBody RemoveRoleFromUserForm removeRoleFromUserForm) {
		String message = userService.removeRolesFromUser(removeRoleFromUserForm.getUserId(),
				removeRoleFromUserForm.getRolesId());
		return ResponseEntity.ok(message);
	}
}
