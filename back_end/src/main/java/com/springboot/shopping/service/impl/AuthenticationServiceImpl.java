package com.springboot.shopping.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.springboot.shopping.dto.auth.AuthenticationRequest;
import com.springboot.shopping.dto.auth.AuthenticationResponse;
import com.springboot.shopping.exception.auth.UserAuthenticationException;
import com.springboot.shopping.security.JwtProvider;
import com.springboot.shopping.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;

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
			System.out.println(user.getUsername());
			// Get user's roles
			List<String> userRoles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
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
