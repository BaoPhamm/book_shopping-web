package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.springboot.shopping.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/role")
@RequiredArgsConstructor
public class RoleAdminController {

	private final RoleService roleService;

	@GetMapping("/{name}")
	public ResponseEntity<RoleResponse> getRoleByName(@PathVariable("name") String roleName) {
		RoleResponse role = roleService.findRoleByName(roleName);
		return ResponseEntity.ok(role);
	}

	@GetMapping()
	public ResponseEntity<List<RoleResponse>> getAllRoles() {
		List<RoleResponse> allRoles = roleService.getAllRoles();
		return ResponseEntity.ok(allRoles);
	}

	@PostMapping()
	public ResponseEntity<RoleResponse> createRole(@Validated @RequestBody RoleRequest roleRequest) {
		RoleResponse createdRole = roleService.createRole(roleRequest.getName());
		return ResponseEntity.ok(createdRole);
	}

	@PutMapping()
	public ResponseEntity<RoleResponse> updateRole(@Validated @RequestBody RoleRequest roleRequest) {
		RoleResponse createdRole = roleService.updateRole(roleRequest.getId(), roleRequest.getName());
		return ResponseEntity.ok(createdRole);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRole(@PathVariable("id") Long roleId) {
		String message = roleService.deleteRole(roleId);
		return ResponseEntity.ok(message);
	}

}
