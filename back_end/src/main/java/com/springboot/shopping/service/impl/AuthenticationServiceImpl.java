package com.springboot.shopping.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.dto.auth.AuthenticationRequest;
import com.springboot.shopping.dto.auth.AuthenticationResponse;
import com.springboot.shopping.exception.auth.PasswordException;
import com.springboot.shopping.exception.auth.UserAuthenticationException;
import com.springboot.shopping.exception.user.PhoneNumberExistException;
import com.springboot.shopping.exception.user.UsernameExistException;
import com.springboot.shopping.mapper.CommonMapper;
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

		if (newUser.getPassword() != null && !newUser.getPassword().equals(registrationRequest.getPasswordRepeat())) {
			throw new PasswordException("Passwords do not match.");
		}

		// Create default role is "USER"
		Optional<Role> role = roleRepository.findByname("USER");

		newUser.getRoles().add(role.get());
		newUser.setBlocked(false);
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		userRepository.save(newUser);
		return "User successfully registered.";
	}

	@Override
	public AuthenticationResponse login(AuthenticationRequest request) {

		String username = request.getUsername();
		String password = request.getPassword();
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
			Map<String, String> credentials = new HashMap<>();
			credentials.put("username", username);
			credentials.put("accessToken", accessToken);
			credentials.put("refreshToken", refreshToken);
			credentials.put("userRoles", userRoles.toString());

			AuthenticationResponse response = new AuthenticationResponse();
			response.setUsername(credentials.get("username"));
			response.setToken(credentials.get("accessToken"));
			response.setRefreshToken(credentials.get("refreshToken"));
			response.setUserRoles(credentials.get("userRoles"));
			return response;

		} catch (AuthenticationException e) {
			throw new UserAuthenticationException();
		}
	}
}
