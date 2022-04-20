package com.springboot.shopping.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.role.RoleRequest;
import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
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

	public UserEntity findUserByUsernameReturnObject(String username) {
		return userService.findUserByUsername(username).get();
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
		UserEntity user = commonMapper.convertToEntity(userRequest, UserEntity.class);
		return commonMapper.convertToResponse(userService.updateProfileUser(userId, user), UserResponse.class);
	}

	public List<UserResponse> deleteUser(Long userId) {
		return commonMapper.convertToResponseList(userService.deleteUser(userId), UserResponse.class);
	}
	
    public UserResponse updateProfile(String username, UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        UserEntity user = commonMapper.convertToEntity(userRequest, UserEntity.class);
        return commonMapper.convertToResponse(userService.updateProfile(username, user), UserResponse.class);
    }
    
	public String passwordReset(String username, PasswordResetRequest passwordReset) {
		return userService.passwordReset(username, passwordReset.getPassword(), passwordReset.getPassword2());
	}

}
