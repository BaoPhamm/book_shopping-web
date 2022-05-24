package com.springboot.shopping.controller.manage.admin;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/manage-admin")
@RequiredArgsConstructor
public class ManageAdminController {

	private final UserService userService;

	// Get admin by ID
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getAdminById(@PathVariable("id") Long adminId) {
		UserResponse userAdminResponse = userService.findAdminById(adminId);
		return ResponseEntity.ok(userAdminResponse);
	}

	// Get All admins
	@GetMapping()
	public ResponseEntity<List<UserResponse>> getAllAdmins(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "25") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		List<UserResponse> allAdmins = userService.findAllAdmins(pageable);
		return ResponseEntity.ok(allAdmins);
	}

	// Delete an existing admin by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAdminByid(@PathVariable("id") Long adminId) {
		String message = userService.deleteAdmin(adminId);
		return ResponseEntity.ok(message);
	}

	// Block an existing admin by ID
	@PutMapping("/block")
	public ResponseEntity<String> blockAdmin(@RequestBody Long UserId) {
		String message = userService.blockAdmin(UserId);
		return ResponseEntity.ok(message);
	}

	// Unblock an existing admin by ID
	@PutMapping("/unblock")
	public ResponseEntity<String> unBlockAdmin(@RequestBody Long UserId) {
		String message = userService.unBlockAdmin(UserId);
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

	// get total admins
	@GetMapping("/total")
	public ResponseEntity<Long> getTotalAdmins() {
		Long totalAdmins = userService.getTotalAdmins();
		return ResponseEntity.ok(totalAdmins);
	}
}
