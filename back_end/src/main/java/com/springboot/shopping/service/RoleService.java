package com.springboot.shopping.service;

import java.util.List;
import java.util.Optional;

import com.springboot.shopping.dto.role.RoleRequest;
import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;

public interface RoleService {

	RoleResponse createRole(String roleName);

}
