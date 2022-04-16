package com.springboot.shopping.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.shopping.dto.role.RoleRequest;
import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.model.Book;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.User;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final CommonMapper commonMapper;
	private final UserService userService;

	public UserResponse findUserById(Long userId) {
		return commonMapper.convertToResponse(userService.findUserById(userId).get(), UserResponse.class);
	}

	public UserResponse findUserByUsername(String username) {
		return commonMapper.convertToResponse(userService.findUserByUsername(username).get(), UserResponse.class);
	}

	public List<UserResponse> findAllUsers() {
		return commonMapper.convertToResponseList(userService.findAllUsers(), UserResponse.class);
	}

	public RoleResponse createRole(RoleRequest roleRequest) {
		Role role = commonMapper.convertToEntity(roleRequest, Role.class);
		return commonMapper.convertToResponse(userService.createRole(role), RoleResponse.class);
	}

	public void addRoleToUser(String username, String roleName) {
		userService.addRoleToUser(username, roleName);
	}

	public UserResponse updateProfileUser(Long userId, UserRequest userRequest) {
		User user = commonMapper.convertToEntity(userRequest, User.class);
		return commonMapper.convertToResponse(userService.updateProfileUser(userId, user), UserResponse.class);

	}

	public List<UserResponse> deleteUser(Long userId) {
		return commonMapper.convertToResponseList(userService.deleteUser(userId), UserResponse.class);
	}

}
