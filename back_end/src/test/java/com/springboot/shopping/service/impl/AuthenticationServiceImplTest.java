package com.springboot.shopping.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.dto.auth.AuthenticationRequest;
import com.springboot.shopping.dto.auth.AuthenticationResponse;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.security.JwtProvider;

class AuthenticationServiceImplTest {

	private AuthenticationServiceImpl authenticationServiceImpl;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private JwtProvider jwtProvider;
	private CommonMapper commonMapper;

	private RegistrationRequest registrationRequestInitial;
	private AuthenticationRequest authenticationRequest;
	private AuthenticationResponse authenticationResponseExpected;
	private UserEntity userInitial;
	private UserEntity savedUserExpected;
	private Role roleInitial;
	private UsernamePasswordAuthenticationToken authenticationToken;
	private Authentication authentication;

	@BeforeEach
	void beforeEach() {
		// Mock AuthenticationManager
		authenticationManager = mock(AuthenticationManager.class);
		// Mock JwtProvider
		jwtProvider = mock(JwtProvider.class);

		authentication = mock(Authentication.class);

		authenticationServiceImpl = new AuthenticationServiceImpl(authenticationManager, jwtProvider);

		registrationRequestInitial = RegistrationRequest.builder().firstName("firstName1").lastName("lastName1")
				.username("username1").password("password1").passwordRepeat("password1").phoneNumber("phoneNumber1")
				.address("address1").build();

		roleInitial = Role.builder().id(1L).name("USER").build();

		userInitial = UserEntity.builder().id(1L).firstName("firstName1").lastName("lastName1").username("username1")
				.password("password1").phoneNumber("phoneNumber1").address("address1").roles(new HashSet<Role>())
				.build();

		savedUserExpected = UserEntity.builder().id(1L).firstName("firstName1").lastName("lastName1")
				.username("username1").password("password1Encoded").phoneNumber("phoneNumber1").address("address1")
				.isBlocked(false).roles((new HashSet<Role>(List.of(roleInitial)))).build();

		authenticationRequest = AuthenticationRequest.builder().username("usernamee").password("passwordd").build();
		authenticationToken = new UsernamePasswordAuthenticationToken("username", "password");
		authenticationResponseExpected = new AuthenticationResponse();
	}

	// UnitTest for function login()
	@Test
	@WithMockUser(username = "usernameeeee", roles = { "USER" })
	void login_ShouldReturnSuccessMessage() {

		Collection<SimpleGrantedAuthority> Authorities = new ArrayList<>();
		Authorities.add(new SimpleGrantedAuthority("USER"));
		User user = new User(authenticationRequest.getUsername(), authenticationRequest.getPassword(), Authorities);
		List<String> userRoles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		String token = "this is a token";
		String refreshToken = "this is a refreshToken";

//		System.out.println(authenticationRequest.getUsername());
		authenticationResponseExpected.setUsername(authenticationRequest.getUsername());
		authenticationResponseExpected.setToken(token);
		authenticationResponseExpected.setRefreshToken(refreshToken);
		authenticationResponseExpected.setUserRoles(userRoles.toString());

//		when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);
//		when(authentication.getPrincipal()).thenReturn(user);
		when(jwtProvider.createToken(user.getUsername(), userRoles)).thenReturn(token);
		when(jwtProvider.createRefreshToken(user.getUsername())).thenReturn(refreshToken);

		AuthenticationResponse authenticationResponse = authenticationServiceImpl.login(authenticationRequest);

		assertThat(authenticationResponse.getUsername(), is(authenticationResponseExpected.getUsername()));
		// assertThat(authenticationResponse.equals(authenticationResponseExpected),
		// is(true));

//		verify(authenticationManager, times(1)).authenticate(authenticationToken);
//		verify(authentication, times(1)).getPrincipal();
		verify(jwtProvider, times(1)).createToken(user.getUsername(), userRoles);
		verify(jwtProvider, times(1)).createRefreshToken(user.getUsername());
	}

}
