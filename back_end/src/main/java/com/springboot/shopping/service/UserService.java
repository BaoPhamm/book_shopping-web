package com.springboot.shopping.service;

import java.util.List;
import java.util.Optional;

import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;

public interface UserService {

	Optional<UserEntity> findUserById(Long userId);

	Optional<UserEntity> findUserByUsername(String username);

	List<UserEntity> findAllUsers();

	Role createRole(Role role);

	String addRoleToUser(String username, String roleName);

	UserEntity updateProfileUser(Long userId, UserEntity user);

	List<UserEntity> deleteUser(Long userId);
	
	UserEntity updateProfile(String email, UserEntity user);
	
	String passwordReset(String username, String password, String password2);

}
