package com.springboot.shopping.service;

import java.util.List;
import java.util.Optional;

import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.User;

public interface UserService {

	Optional<User> findUserById(Long userId);

	Optional<User> findUserByUsername(String username);

	List<User> findAllUsers();

	Role createRole(Role role);

	void addRoleToUser(String username, String roleName);

	User updateProfileUser(Long userId, User user);

	List<User> deleteUser(Long userId);

}
