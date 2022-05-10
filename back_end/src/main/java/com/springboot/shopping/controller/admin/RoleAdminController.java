package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<RoleResponse> createRole(@RequestBody String roleName) {
		RoleResponse createdRole = roleService.createRole(roleName);
		return ResponseEntity.ok(createdRole);
	}

	@DeleteMapping()
	public ResponseEntity<RoleResponse> deleteRole(@RequestBody String roleName) {
		RoleResponse createdRole = roleService.createRole(roleName);
		return ResponseEntity.ok(createdRole);
	}

}
