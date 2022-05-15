package com.springboot.shopping.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.springboot.shopping.dto.auth.AuthenticationRequest;
import com.springboot.shopping.dto.auth.AuthenticationResponse;
import com.springboot.shopping.exception.auth.UserAuthenticationException;
import com.springboot.shopping.security.JwtProvider;

class AuthenticationServiceImplTest {

	private AuthenticationServiceImpl authenticationServiceImpl;
	private AuthenticationManager authenticationManager;
	private JwtProvider jwtProvider;

	private AuthenticationRequest authenticationRequest;
	private AuthenticationResponse authenticationResponseExpected;

	@BeforeEach
	void beforeEach() {
		// Mock AuthenticationManager
		authenticationManager = mock(AuthenticationManager.class);
		// Mock JwtProvider
		jwtProvider = mock(JwtProvider.class);

		authenticationServiceImpl = new AuthenticationServiceImpl(authenticationManager, jwtProvider);

		authenticationRequest = AuthenticationRequest.builder().username("usernamee").password("passwordd").build();
		authenticationResponseExpected = new AuthenticationResponse();
	}

	// UnitTest for function login()
	@Test
	void login_ShouldReturnSuccessresponse() {

		Collection<SimpleGrantedAuthority> Authorities = new ArrayList<>();
		Authorities.add(new SimpleGrantedAuthority("USER"));
		User user = new User(authenticationRequest.getUsername(), authenticationRequest.getPassword(), Authorities);
		List<String> userRoles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		String token = "this is a token";
		String refreshToken = "this is a refreshToken";

		authenticationResponseExpected.setUsername("usernamee");
		authenticationResponseExpected.setToken(token);
		authenticationResponseExpected.setRefreshToken(refreshToken);
		authenticationResponseExpected.setUserRoles(userRoles.toString());

		Authentication authentication = mock(Authentication.class);

		when(authenticationManager.authenticate(isA(Authentication.class))).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);
		when(jwtProvider.createToken(user.getUsername(), userRoles)).thenReturn(token);
		when(jwtProvider.createRefreshToken(user.getUsername())).thenReturn(refreshToken);

		AuthenticationResponse authenticationResponse = authenticationServiceImpl.login(authenticationRequest);

		assertThat(authenticationResponse.getUsername(), is(authenticationResponseExpected.getUsername()));
		assertThat(authenticationResponse.equals(authenticationResponseExpected), is(true));

		verify(authenticationManager, times(1)).authenticate(isA(Authentication.class));
		verify(authentication, times(1)).getPrincipal();
		verify(jwtProvider, times(1)).createToken(user.getUsername(), userRoles);
		verify(jwtProvider, times(1)).createRefreshToken(user.getUsername());
	}

	@Test
	void login_ShouldThrowUserAuthenticationException() {

		Collection<SimpleGrantedAuthority> Authorities = new ArrayList<>();
		Authorities.add(new SimpleGrantedAuthority("USER"));
		User user = new User(authenticationRequest.getUsername(), authenticationRequest.getPassword(), Authorities);
		List<String> userRoles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		String token = "this is a token";
		String refreshToken = "this is a refreshToken";

		authenticationResponseExpected.setUsername("usernamee");
		authenticationResponseExpected.setToken(token);
		authenticationResponseExpected.setRefreshToken(refreshToken);
		authenticationResponseExpected.setUserRoles(userRoles.toString());

		Authentication authentication = mock(Authentication.class);
		authentication.setAuthenticated(false);

		when(authenticationManager.authenticate(isA(Authentication.class))).thenThrow(BadCredentialsException.class);

		Exception exception = assertThrows(UserAuthenticationException.class,
				() -> authenticationServiceImpl.login(authenticationRequest));
		assertThat(exception.getMessage(), is("Incorrect password or username"));

		verify(authenticationManager, times(1)).authenticate(isA(Authentication.class));
	}
}
