package com.springboot.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.role.RoleRequest;
import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;

public interface UserService {

	UserResponse findUserById(Long userId);

	UserResponse findUserByUsername(String username);
	
	UserEntity findUserByUsernameReturnUserEntity(String username);

	List<UserResponse> findAllUsers();

	String addRoleToUser(String username, String roleName);

	List<UserResponse> deleteUser(Long userId);
	
	UserResponse updateProfile(String username, UserRequest userRequest, BindingResult bindingResult);
	
	String passwordReset(String username, PasswordResetRequest passwordReset);

}
