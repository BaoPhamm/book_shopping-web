package com.springboot.shopping.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.shopping.exception.PasswordConfirmationException;
import com.springboot.shopping.exception.UserExistException;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.User;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	// private final PasswordEncoder passwordEncoder;

	@Override
	public String registerUser(User user, String password2) {

		Optional<Role> role = roleRepository.findByname("USER");

		if (user.getPassword() != null && !user.getPassword().equals(password2)) {
			throw new PasswordConfirmationException("Passwords do not match.");
		}
		Optional<User> userFromDb = userRepository.findByUsername(user.getUsername());
		if (userFromDb.isPresent()) {
			throw new UserExistException("UserName is already used.");
		}

		Optional<User> checkUserPhoneNumFromDb = userRepository.findByUsername(user.getPhoneNumber());
		if (checkUserPhoneNumFromDb.isPresent()) {
			throw new UserExistException("Phone number is already used.");
		}

		user.getRoles().add(role.get());
		// user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User successfully registered.";
	}

}
