package com.springboot.shopping.service.impl;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.exception.auth.PasswordException;
import com.springboot.shopping.exception.role.RoleNotFoundException;
import com.springboot.shopping.exception.user.AdminSelfDeleteException;
import com.springboot.shopping.exception.user.PhoneNumberExistException;
import com.springboot.shopping.exception.user.UserNotFoundException;
import com.springboot.shopping.exception.user.UserRoleExistException;
import com.springboot.shopping.exception.user.UserRoleNotFoundException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.security.JwtProvider;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
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
		List<UserEntity> allUsers = userRepository.findAll();
		return commonMapper.convertToResponseList(allUsers, UserResponse.class);
	}

	@Override
	public UserResponse updateProfile(String username, UserRequest userRequest) {
		UserEntity newUserInfo = commonMapper.convertToEntity(userRequest, UserEntity.class);
		Optional<UserEntity> userFromDb = userRepository.findByUsername(username);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		Optional<UserEntity> checkUserPhoneNumFromDb = userRepository.findByPhoneNumber(newUserInfo.getPhoneNumber());
		if (!newUserInfo.getPhoneNumber().equals(userFromDb.get().getPhoneNumber())
				&& checkUserPhoneNumFromDb.isPresent()) {
			throw new PhoneNumberExistException();
		}
		userFromDb.get().setFirstName(newUserInfo.getFirstName());
		userFromDb.get().setLastName(newUserInfo.getLastName());
		userFromDb.get().setAddress(newUserInfo.getAddress());
		userFromDb.get().setPhoneNumber(newUserInfo.getPhoneNumber());
		UserEntity savedUser = userRepository.save(userFromDb.get());
		return commonMapper.convertToResponse(savedUser, UserResponse.class);
	}

	@Override
	public String deleteUser(Long userId, String adminUsername) {

		Optional<UserEntity> userFromDb = userRepository.findById(userId);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		Optional<UserEntity> adminFromDb = userRepository.findByUsername(adminUsername);
		if (adminFromDb.get().getId() == userId) {
			throw new AdminSelfDeleteException();
		}
		userRepository.deleteById(userId);
		return "User successfully deleted.";
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
	public String removeRoleFromUser(String username, String roleName) {

		Optional<UserEntity> userFromDb = userRepository.findByUsername(username);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		Optional<Role> roleFromDb = roleRepository.findByname(roleName);
		if (roleFromDb.isEmpty()) {
			throw new RoleNotFoundException();
		}
		if (!userFromDb.get().getRoles().contains(roleFromDb.get())) {
			throw new UserRoleNotFoundException();
		}
		userFromDb.get().getRoles().remove(roleFromDb.get());
		userRepository.save(userFromDb.get());
		return "Role successfully removed.";
	}

	@Override
	public String passwordReset(String username, PasswordResetRequest passwordResetRequest) {

		Optional<UserEntity> userFromDb = userRepository.findByUsername(username);
		if (userFromDb.isEmpty()) {
			throw new UserNotFoundException();
		}
		String currentPassword = passwordResetRequest.getCurrentPassword();
		String newPassword = passwordResetRequest.getNewPassword();
		String newPasswordRepeat = passwordResetRequest.getNewPasswordRepeat();

		if (currentPassword.isBlank() || newPassword.isBlank() || newPasswordRepeat.isBlank()) {
			throw new PasswordException("Current password and new password cannot be blank.");
		}

		if (!passwordEncoder.matches(currentPassword, userFromDb.get().getPassword())) {
			throw new PasswordException("Current password is wrong.");
		}

		if (!newPassword.equals(newPasswordRepeat)) {
			throw new PasswordException("Passwords do not match.");
		}

		userFromDb.get().setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(userFromDb.get());
		return "Password successfully changed!";
	}

	@Override
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Get header from request
		String authorizationHeader = request.getHeader(AUTHORIZATION);

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				// Get username from header
				String username = jwtProvider.getUsername(authorizationHeader);
				// Get User entity from DB
				UserEntity userFromDb = findUserByUsernameReturnUserEntity(username);
				// Get User roles
				List<String> userRoles = userFromDb.getRoles().stream().map(Role::getName).collect(Collectors.toList());
				// Create new accessToken
				String accessToken = jwtProvider.createToken(username, userRoles);

				Map<String, String> tokens = new HashMap<String, String>();
				tokens.put("accessToken", accessToken);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				response.setHeader("Header", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());

				Map<String, String> errors = new HashMap<String, String>();
				errors.put("Error_message", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), errors);
			}

		} else {
			throw new RuntimeException("Refesh token is missing!");
		}
	}

}
