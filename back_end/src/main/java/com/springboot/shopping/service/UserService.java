package com.springboot.shopping.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
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

	List<UserResponse> findAllUsers(Pageable pageable);

	String addRolesToUser(Long userId, List<Long> rolesId);

	String removeRolesFromUser(Long userId, List<Long> rolesId);

	String deleteUser(Long userId, String adminUsername);

	String blockUser(Long userId, String adminUsername);

	String unBlockUser(Long userId);

	UserResponse updateProfile(String username, UserRequest userRequest);

	String passwordReset(String username, PasswordResetRequest passwordReset);

	void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
