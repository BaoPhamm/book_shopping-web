package com.springboot.shopping.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.exception.auth.PasswordException;
import com.springboot.shopping.exception.role.RoleNotFoundException;
import com.springboot.shopping.exception.user.PhoneNumberExistException;
import com.springboot.shopping.exception.user.UsernameExistException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.service.RegistrationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final CommonMapper commonMapper;

	@Value("${jwt.secret}")
	private String secretKey;

	@Override
	public String registerUser(RegistrationRequest registrationRequest) {
		UserEntity newUser = commonMapper.convertToEntity(registrationRequest, UserEntity.class);

		Optional<UserEntity> userFromDb = userRepository.findByUsername(newUser.getUsername());
		if (userFromDb.isPresent()) {
			throw new UsernameExistException();
		}

		Optional<UserEntity> checkUserPhoneNumFromDb = userRepository.findByPhoneNumber(newUser.getPhoneNumber());
		if (checkUserPhoneNumFromDb.isPresent()) {
			throw new PhoneNumberExistException();
		}

		if (newUser.getPassword().isBlank()) {
			throw new PasswordException("Password can not be blank.");
		} else if (!newUser.getPassword().equals(registrationRequest.getPasswordRepeat())) {
			throw new PasswordException("Passwords do not match.");
		}

		// Create default role is "USER"
		Role role = roleRepository.findByname("USER").orElseThrow(() -> new RoleNotFoundException());

		newUser.getRoles().add(role);
		newUser.setBlocked(false);
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		userRepository.save(newUser);
		return "User successfully registered.";
	}
}
