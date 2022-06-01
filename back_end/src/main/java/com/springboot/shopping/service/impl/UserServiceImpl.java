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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.springboot.shopping.exception.user.RemoveDefaultRoleException;
import com.springboot.shopping.exception.user.PhoneNumberExistException;
import com.springboot.shopping.exception.user.UserAlreadyBlockedException;
import com.springboot.shopping.exception.user.UserNotBlockedException;
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
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException()));

		Collection<SimpleGrantedAuthority> Authorities = new ArrayList<>();
		userFromDb.get().getRoles().forEach(role -> Authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new User(userFromDb.get().getUsername(), userFromDb.get().getPassword(), Authorities);
	}

	@Override
	public UserResponse findUserById(Long userId) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()));

		List<String> userRoles = userFromDb.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
		if (userRoles.contains("ADMIN") || userRoles.contains("ADMANAGER")) {
			throw new UserNotFoundException();
		}
		return commonMapper.convertToResponse(userFromDb.get(), UserResponse.class);
	}

	@Override
	public UserResponse findAdminById(Long adminId) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException()));

		List<String> userRoles = userFromDb.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
		if (userRoles.contains("ADMANAGER")) {
			throw new UserNotFoundException();
		}
		return commonMapper.convertToResponse(userFromDb.get(), UserResponse.class);
	}

	@Override
	public UserResponse findUserByUsername(String username) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException()));
		return commonMapper.convertToResponse(userFromDb.get(), UserResponse.class);
	}

	@Override
	public UserEntity findUserByUsernameReturnUserEntity(String username) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException()));
		return userFromDb.get();
	}

	@Override
	public List<UserResponse> findAllUsers(Pageable pageable) {
		Page<UserEntity> page = userRepository.findAllUsers(pageable);
		List<UserEntity> allUsers = page.getContent();
		return commonMapper.convertToResponseList(allUsers, UserResponse.class);
	}

	@Override
	public List<UserResponse> findAllAdmins(Pageable pageable) {
		Page<UserEntity> page = userRepository.findAllAdmins(pageable);
		List<UserEntity> allAdmins = page.getContent();
		return commonMapper.convertToResponseList(allAdmins, UserResponse.class);
	}

	@Override
	public UserResponse updateProfile(String username, UserRequest userRequest) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException()));
		Optional<UserEntity> checkUserPhoneNumFromDb = userRepository.findByPhoneNumber(userRequest.getPhoneNumber());

		if (!userRequest.getPhoneNumber().equals(userFromDb.get().getPhoneNumber())
				&& checkUserPhoneNumFromDb.isPresent()) {
			throw new PhoneNumberExistException();
		}
		userFromDb.get().setFirstName(userRequest.getFirstName());
		userFromDb.get().setLastName(userRequest.getLastName());
		userFromDb.get().setAddress(userRequest.getAddress());
		userFromDb.get().setPhoneNumber(userRequest.getPhoneNumber());
		UserEntity savedUser = userRepository.save(userFromDb.get());
		return commonMapper.convertToResponse(savedUser, UserResponse.class);
	}

	@Override
	public String deleteUser(Long userId) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()));

		List<String> userRoles = userFromDb.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
		if (userRoles.contains("ADMIN") || userRoles.contains("ADMANAGER")) {
			throw new UserNotFoundException();
		}
		userRepository.deleteById(userId);
		return "User successfully deleted.";
	}

	@Override
	public String deleteAdmin(Long adminId) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException()));

		List<String> userRoles = userFromDb.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
		if (userRoles.contains("ADMANAGER")) {
			throw new UserNotFoundException();
		}
		userRepository.deleteById(adminId);
		return "User successfully deleted.";
	}

	@Override
	public String blockUser(Long userId) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()));

		List<String> userRoles = userFromDb.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
		if (userRoles.contains("ADMIN") || userRoles.contains("ADMANAGER")) {
			throw new UserNotFoundException();
		} else if (userFromDb.get().isBlocked()) {
			throw new UserAlreadyBlockedException();
		}
		userFromDb.get().setBlocked(true);
		userRepository.save(userFromDb.get());
		return "User successfully blocked.";
	}

	@Override
	public String unBlockUser(Long userId) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()));

		List<String> userRoles = userFromDb.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
		if (userRoles.contains("ADMIN") || userRoles.contains("ADMANAGER")) {
			throw new UserNotFoundException();
		} else if (!userFromDb.get().isBlocked()) {
			throw new UserNotBlockedException();
		}

		userFromDb.get().setBlocked(false);
		userRepository.save(userFromDb.get());
		return "User successfully unblocked.";
	}

	@Override
	public String blockAdmin(Long userId) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()));

		List<String> userRoles = userFromDb.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
		if (userRoles.contains("ADMANAGER")) {
			throw new UserNotFoundException();
		} else if (userFromDb.get().isBlocked()) {
			throw new UserAlreadyBlockedException();
		}
		userFromDb.get().setBlocked(true);
		userRepository.save(userFromDb.get());
		return "User successfully blocked.";
	}

	@Override
	public String unBlockAdmin(Long userId) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()));

		List<String> userRoles = userFromDb.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
		if (userRoles.contains("ADMANAGER")) {
			throw new UserNotFoundException();
		} else if (!userFromDb.get().isBlocked()) {
			throw new UserNotBlockedException();
		}
		userFromDb.get().setBlocked(false);
		userRepository.save(userFromDb.get());
		return "User successfully unblocked.";
	}

	private void findRoleToThrowExcepion(List<Role> allRoles, Long roleId) {
		for (Role role : allRoles) {
			if (role.getId() != roleId) {
				throw new RoleNotFoundException(roleId);
			}
		}
	}

	@Override
	public String addRolesToUser(Long userId, List<Long> roleIds) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()));

		List<Long> userRoleIds = userRepository.findAllIdsOfUserRoles(userId);
		List<Role> allValidRoles = roleRepository.findAllById(roleIds);

		if (allValidRoles.size() == 0) {
			throw new RoleNotFoundException(roleIds.get(0));
		} else if (allValidRoles.size() < roleIds.size()) {
			roleIds.stream().forEach(roleId -> {
				findRoleToThrowExcepion(allValidRoles, roleId);
			});
		} else if (allValidRoles.size() == roleIds.size()) {
			allValidRoles.forEach((role) -> {
				if (role.getName().equals("ADMANAGER")) {
					throw new RoleNotFoundException(role.getId());
				}
			});
			roleIds.forEach(roleId -> {
				if (userRoleIds.contains(roleId)) {
					throw new UserRoleExistException(roleId);
				}
			});
		}

		userFromDb.get().getRoles().addAll(allValidRoles);
		userRepository.save(userFromDb.get());
		return "Role successfully added.";
	}

	@Override
	public String removeRolesFromUser(Long userId, List<Long> roleIds) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()));

		List<Long> userRoleIds = userRepository.findAllIdsOfUserRoles(userId);
		List<Role> allValidRoles = roleRepository.findAllById(roleIds);

		if (allValidRoles.size() == 0) {
			throw new RoleNotFoundException(roleIds.get(0));
		} else if (allValidRoles.size() < roleIds.size()) {
			roleIds.stream().forEach(roleId -> {
				findRoleToThrowExcepion(allValidRoles, roleId);
			});
		} else if (allValidRoles.size() == roleIds.size()) {
			allValidRoles.forEach((role) -> {
				if (role.getName().equals("USER")) {
					throw new RemoveDefaultRoleException();
				}
			});
			roleIds.forEach(roleId -> {
				if (!userRoleIds.contains(roleId)) {
					throw new UserRoleNotFoundException(roleId);
				}
			});
		}

		userFromDb.get().getRoles().removeAll(allValidRoles);
		userRepository.save(userFromDb.get());
		return "Role successfully removed.";
	}

	@Override
	public String passwordReset(String username, PasswordResetRequest passwordResetRequest) {
		Optional<UserEntity> userFromDb = Optional
				.of(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException()));

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
				String username = jwtProvider.getUsername(authorizationHeader);
				UserEntity userFromDb = findUserByUsernameReturnUserEntity(username);
				List<String> userRoles = userFromDb.getRoles().stream().map(Role::getName).collect(Collectors.toList());
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

	@Override
	public Long getTotalUsers() {
		long totalUsers = userRepository.countTotalUsers();
		return totalUsers;
	}

	@Override
	public Long getTotalAdmins() {
		long totalAdmins = userRepository.countTotalAdmins();
		return totalAdmins;
	}

}
