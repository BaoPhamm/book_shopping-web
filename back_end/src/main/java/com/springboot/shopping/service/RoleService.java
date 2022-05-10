package com.springboot.shopping.service;

import java.util.List;

import com.springboot.shopping.dto.role.RoleResponse;

public interface RoleService {

	RoleResponse findRoleByName(String roleName);

	List<RoleResponse> getAllRoles();

	RoleResponse createRole(String roleName);

	RoleResponse updateRole(String roleName);

	String deleteRole(Long roleId);
}
