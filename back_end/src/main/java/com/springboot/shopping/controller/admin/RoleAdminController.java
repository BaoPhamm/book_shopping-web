package com.springboot.shopping.controller.admin;

import org.springframework.http.ResponseEntity;
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

	@PostMapping()
	public ResponseEntity<RoleResponse> createRole(@RequestBody String roleName) {
		RoleResponse createdRole = roleService.createRole(roleName);
		return ResponseEntity.ok(createdRole);
	}
}
