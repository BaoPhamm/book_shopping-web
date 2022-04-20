package com.springboot.shopping.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springboot.shopping.exception.ApiRequestException;
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
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	@Value("${jwt.secret}")
	private String secretKey;

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
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User successfully registered.";
	}

	@Override
	public Map<String, String> login(String username, String password) {

		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
					password);
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication
					.getPrincipal();
			Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());

			List<String> userRoles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			String access_token = JWT.create().withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)).withClaim("roles", userRoles)
					.sign(algorithm);

			String refresh_token = JWT.create().withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).sign(algorithm);

			Map<String, String> response = new HashMap<>();
			response.put("username", username);
			response.put("access_token", access_token);
			response.put("refresh_token", refresh_token);
			response.put("userRoles", userRoles.toString());
			return response;
		} catch (AuthenticationException e) {
			throw new ApiRequestException("Incorrect password or email", HttpStatus.FORBIDDEN);
		}

	}
}
