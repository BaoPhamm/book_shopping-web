package com.springboot.shopping.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.shopping.exception.ApiRequestException;
import com.springboot.shopping.exception.PasswordConfirmationException;
import com.springboot.shopping.exception.UserExistException;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.security.JwtProvider;
import com.springboot.shopping.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;

	@Value("${jwt.secret}")
	private String secretKey;

	@Override
	public String registerUser(UserEntity user, String password2) {

		// Create default role is "USER"
		Optional<Role> role = roleRepository.findByname("USER");

		if (user.getPassword() != null && !user.getPassword().equals(password2)) {
			throw new PasswordConfirmationException("Passwords do not match.");
		}
		Optional<UserEntity> userFromDb = userRepository.findByUsername(user.getUsername());
		if (userFromDb.isPresent()) {
			throw new UserExistException("UserName is already used.");
		}

		Optional<UserEntity> checkUserPhoneNumFromDb = userRepository.findByUsername(user.getPhoneNumber());
		if (checkUserPhoneNumFromDb.isPresent()) {
			throw new UserExistException("Phone number is already used.");
		}

		user.getRoles().add(role.get());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User successfully registered.";
	}

	@Override
	public Map<String, String> login(String username, String password) {

		try {
			// Create authenticationToken
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
					password);
			// Authenticate User
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			// Get user Principal
			User user = (User) authentication.getPrincipal();
			// Get user's roles
			List<String> userRoles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			// Create access Token and refresh Token
			String accessToken = jwtProvider.createToken(user.getUsername(), userRoles);
			String refreshToken = jwtProvider.createRefreshToken(user.getUsername());

			// Put attributes into response
			Map<String, String> response = new HashMap<>();
			response.put("username", username);
			response.put("accessToken", accessToken);
			response.put("refreshToken", refreshToken);
			response.put("userRoles", userRoles.toString());
			return response;
		} catch (AuthenticationException e) {
			throw new ApiRequestException("Incorrect password or email", HttpStatus.FORBIDDEN);
		}
	}

	@Override
	public String passwordReset(String username, String password, String password2) {
		if (password2.isBlank()) {
			throw new PasswordConfirmationException("Password confirmation cannot be blank.");
		}
		if (password != null && !password.equals(password2)) {
			throw new PasswordConfirmationException("Passwords do not match.");
		}
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiRequestException("User not found.", HttpStatus.NOT_FOUND));
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		return "Password successfully changed!";

	}
}
