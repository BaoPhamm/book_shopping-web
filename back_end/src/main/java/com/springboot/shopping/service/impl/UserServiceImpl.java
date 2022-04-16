package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.shopping.exception.ApiRequestException;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.User;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Override
	public Optional<User> findUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public User updateProfileUser(Long userId, User user) {
		User userFromDb = userRepository.findById(userId)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		userFromDb.setFirstName(user.getFirstName());
		userFromDb.setLastName(user.getLastName());
		userFromDb.setPhoneNumber(user.getPhoneNumber());
		userFromDb.setAddress(user.getAddress());
		return userRepository.save(userFromDb);
	}

	@Override
	public List<User> deleteUser(Long userId) {
		User userFromDb = userRepository.findById(userId)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		userRepository.deleteById(userId);
		return userRepository.findAll();
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		Optional<User> user = userRepository.findByUsername(username);
		Optional<Role> role = roleRepository.findByname(roleName);
		user.get().getRoles().add(role.get());
	}

}
