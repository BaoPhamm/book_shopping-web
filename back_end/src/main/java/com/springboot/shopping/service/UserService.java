package com.springboot.shopping.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.model.UserEntity;

public interface UserService {

	UserResponse findUserById(Long userId);
	
	UserResponse findAdminById(Long adminId);

	UserResponse findUserByUsername(String username);

	UserEntity findUserByUsernameReturnUserEntity(String username);

	List<UserResponse> findAllUsers(Pageable pageable);

	List<UserResponse> findAllAdmins(Pageable pageable);

	String addRolesToUser(Long userId, List<Long> rolesId);

	String removeRolesFromUser(Long userId, List<Long> rolesId);

	String deleteUser(Long userId);
	
	String deleteAdmin(Long userId);

	String blockUser(Long userId);

	String unBlockUser(Long userId);

	String blockAdmin(Long userId);

	String unBlockAdmin(Long userId);

	UserResponse updateProfile(String username, UserRequest userRequest);

	String passwordReset(String username, PasswordResetRequest passwordReset);

	void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

	Long getTotalUsers();

	Long getTotalAdmins();

}
