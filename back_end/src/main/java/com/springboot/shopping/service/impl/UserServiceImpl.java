package com.springboot.shopping.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.role.RoleRequest;
import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.exception.ApiRequestException;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.exception.PasswordConfirmationException;
import com.springboot.shopping.exception.RoleExistException;
import com.springboot.shopping.exception.UserNotFoundException;
import com.springboot.shopping.exception.UserRoleExistException;
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
		Optional<UserEntity> userEntity = userRepository.findByUsername(username);
		if (!userEntity.isPresent()) {
			throw new UserNotFoundException();
		}
		Collection<SimpleGrantedAuthority> Authorities = new ArrayList<>();
		userEntity.get().getRoles().forEach(role -> Authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new User(userEntity.get().getUsername(), userEntity.get().getPassword(), Authorities);
	}

	@Override
	public UserResponse findUserById(Long userId) {
		UserEntity userFromDB = userRepository.findById(userId)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		return commonMapper.convertToResponse(userFromDB, UserResponse.class);
	}

	@Override
	public UserResponse findUserByUsername(String username) {
		UserEntity userFromDB = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		return commonMapper.convertToResponse(userFromDB, UserResponse.class);
	}

	@Override
	public UserEntity findUserByUsernameReturnUserEntity(String username) {
		UserEntity userFromDB = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		return userFromDB;
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
		UserEntity userFromDb = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiRequestException("Username not found.", HttpStatus.NOT_FOUND));
		userFromDb.setFirstName(newUserInfo.getFirstName());
		userFromDb.setLastName(newUserInfo.getLastName());
		userFromDb.setAddress(newUserInfo.getAddress());
		userFromDb.setPhoneNumber(newUserInfo.getPhoneNumber());

		return commonMapper.convertToResponse(userRepository.save(userFromDb), UserResponse.class);
	}

	@Override
	public List<UserResponse> deleteUser(Long userId) {
		UserEntity userFromDb = userRepository.findById(userId)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		userRepository.deleteById(userId);

		return commonMapper.convertToResponseList(userRepository.findAll(), UserResponse.class);
	}

	@Override
	public String addRoleToUser(String username, String roleName) {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		Role role = roleRepository.findByname(roleName)
				.orElseThrow(() -> new ApiRequestException("Role not found!", HttpStatus.NOT_FOUND));
		if (user.getRoles().contains(role)) {
			throw new UserRoleExistException("User already has this role !");
		}
		user.getRoles().add(role);
		userRepository.save(user);
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
		UserEntity userFromDb = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiRequestException("User not found.", HttpStatus.NOT_FOUND));
		userFromDb.setPassword(passwordEncoder.encode(password));
		userRepository.save(userFromDb);
		return "Password successfully changed!";

	}

}
