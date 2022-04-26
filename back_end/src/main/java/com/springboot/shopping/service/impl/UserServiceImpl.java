package com.springboot.shopping.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.exception.auth.PasswordConfirmationException;
import com.springboot.shopping.exception.role.RoleNotFoundException;
import com.springboot.shopping.exception.user.UserNotFoundException;
import com.springboot.shopping.exception.user.UserRoleExistException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final CommonMapper commonMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userFromDb = userRepository.findByUsername(username);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		Collection<SimpleGrantedAuthority> Authorities = new ArrayList<>();
		userFromDb.get().getRoles().forEach(role -> Authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new User(userFromDb.get().getUsername(), userFromDb.get().getPassword(), Authorities);
	}

	@Override
	public UserResponse findUserById(Long userId) {
		Optional<UserEntity> userFromDb = userRepository.findById(userId);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		return commonMapper.convertToResponse(userFromDb.get(), UserResponse.class);
	}

	@Override
	public UserResponse findUserByUsername(String username) {
		Optional<UserEntity> userFromDb = userRepository.findByUsername(username);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		return commonMapper.convertToResponse(userFromDb.get(), UserResponse.class);
	}

	@Override
	public UserEntity findUserByUsernameReturnUserEntity(String username) {
		Optional<UserEntity> userFromDb = userRepository.findByUsername(username);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		return userFromDb.get();
	}

	@Override
	public List<UserResponse> findAllUsers() {
		return commonMapper.convertToResponseList(userRepository.findAll(), UserResponse.class);
	}

	@Override
	public UserResponse updateProfile(String username, UserRequest userRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		}
		UserEntity newUserInfo = commonMapper.convertToEntity(userRequest, UserEntity.class);
		Optional<UserEntity> userFromDb = userRepository.findByUsername(username);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		userFromDb.get().setFirstName(newUserInfo.getFirstName());
		userFromDb.get().setLastName(newUserInfo.getLastName());
		userFromDb.get().setAddress(newUserInfo.getAddress());
		userFromDb.get().setPhoneNumber(newUserInfo.getPhoneNumber());

		return commonMapper.convertToResponse(userRepository.save(userFromDb.get()), UserResponse.class);
	}

	@Override
	public List<UserResponse> deleteUser(Long userId) {
		Optional<UserEntity> userFromDb = userRepository.findById(userId);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		userRepository.deleteById(userId);

		return commonMapper.convertToResponseList(userRepository.findAll(), UserResponse.class);
	}

	@Override
	public String addRoleToUser(String username, String roleName) {
		Optional<UserEntity> userFromDb = userRepository.findByUsername(username);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		Optional<Role> roleFromDb = roleRepository.findByname(roleName);
		if (roleFromDb.isEmpty()) {
			throw new RoleNotFoundException();
		}
		if (userFromDb.get().getRoles().contains(roleFromDb.get())) {
			throw new UserRoleExistException();
		}
		userFromDb.get().getRoles().add(roleFromDb.get());
		userRepository.save(userFromDb.get());
		return "Role successfully added.";
	}

	@Override
	public String passwordReset(String username, PasswordResetRequest passwordResetRequest) {

		String password = passwordResetRequest.getPassword();
		String passwordRepeat = passwordResetRequest.getPasswordRepeat();

		if (passwordRepeat.isBlank()) {
			throw new PasswordConfirmationException("Password confirmation cannot be blank.");
		}
		if (password != null && !password.equals(passwordRepeat)) {
			throw new PasswordConfirmationException("Passwords do not match.");
		}
		Optional<UserEntity> userFromDb = userRepository.findByUsername(username);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		userFromDb.get().setPassword(passwordEncoder.encode(password));
		userRepository.save(userFromDb.get());
		return "Password successfully changed!";

	}

}
