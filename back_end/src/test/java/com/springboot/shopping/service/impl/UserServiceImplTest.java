package com.springboot.shopping.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.exception.user.PhoneNumberExistException;
import com.springboot.shopping.exception.user.UserNotFoundException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.security.JwtProvider;

class UserServiceImplTest {

	private UserServiceImpl userServiceImpl;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtProvider jwtProvider;
	private CommonMapper commonMapper;

	private Role roleInitial;
	private UserEntity userInitial;

	private UserDetails userDetailsExpected;
	private UserResponse userResponseExpected;

	private UserRequest userRequestInitial;

	@BeforeEach
	void beforeEach() {
		// Mock UserRepository
		userRepository = mock(UserRepository.class);
		// Mock RoleRepository
		roleRepository = mock(RoleRepository.class);
		// Mock PasswordEncoder
		passwordEncoder = mock(PasswordEncoder.class);
		// Mock JwtProvider
		jwtProvider = mock(JwtProvider.class);
		// Mock CommonMapper
		commonMapper = mock(CommonMapper.class);

		userServiceImpl = new UserServiceImpl(userRepository, roleRepository, passwordEncoder, jwtProvider,
				commonMapper);

		roleInitial = Role.builder().id(1L).name("USER").build();

		userInitial = UserEntity.builder().id(1L).firstName("firstName1").lastName("lastName1").username("username1")
				.password("password1").phoneNumber("phoneNumber1").address("address1").isBlocked(false)
				.roles((new HashSet<Role>(List.of(roleInitial)))).build();

		userResponseExpected = UserResponse.builder().id(1L).firstName("firstName1").lastName("lastName1")
				.username("username1").phoneNumber("phoneNumber1").address("address1").isBlocked(false)
				.roles((new HashSet<Role>(List.of(roleInitial)))).build();

		userRequestInitial = UserRequest.builder().firstName("firstName1Updated").lastName("lastName1Updated")
				.phoneNumber("phoneNumber1Updated").address("address1Updated").build();
	}

	// UnitTest for function loadUserByUsername()
	@Test
	void loadUserByUsername_ShouldReturnUser() {

		String username = "username";
		Collection<SimpleGrantedAuthority> Authorities = new ArrayList<>();
		Authorities.add(new SimpleGrantedAuthority("USER"));
		userDetailsExpected = new User("username1", "password1", Authorities);

		when(userRepository.findByUsername(username)).thenReturn(Optional.of(userInitial));

		UserDetails userDetailsResult = userServiceImpl.loadUserByUsername(username);

		assertThat(userDetailsResult.getUsername(), is(userDetailsExpected.getUsername()));
		assertThat(userDetailsResult.getPassword(), is(userDetailsExpected.getPassword()));
		assertThat(userDetailsResult.getAuthorities(), is(userDetailsExpected.getAuthorities()));

		verify(userRepository, times(1)).findByUsername(username);
	}

	@Test
	void loadUserByUsername_ShouldUserNotFoundException_WhenUsernameNotFound() {

		String username = "username";

		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

		Exception exception = assertThrows(UserNotFoundException.class,
				() -> userServiceImpl.loadUserByUsername(username));
		assertThat(exception.getMessage(), is("User not found!"));

		verify(userRepository, times(1)).findByUsername(username);
	}

	// UnitTest for function findUserById()
	@Test
	void findUserById_ShouldReturnUserResponse() {

		when(userRepository.findById(userInitial.getId())).thenReturn(Optional.of(userInitial));
		when(commonMapper.convertToResponse(userInitial, UserResponse.class)).thenReturn(userResponseExpected);

		UserResponse userResponseResult = userServiceImpl.findUserById(userInitial.getId());

		assertThat(userResponseResult, is(userResponseExpected));

		verify(userRepository, times(1)).findById(userInitial.getId());
		verify(commonMapper, times(1)).convertToResponse(userInitial, UserResponse.class);
	}

	@Test
	void findUserById_ShouldUserNotFoundException_WhenUserNotFound() {

		when(userRepository.findById(userInitial.getId())).thenReturn(Optional.empty());

		Exception exception = assertThrows(UserNotFoundException.class,
				() -> userServiceImpl.findUserById(userInitial.getId()));
		assertThat(exception.getMessage(), is("User not found!"));

		verify(userRepository, times(1)).findById(userInitial.getId());
	}

	// UnitTest for function findUserByUsername()
	@Test
	void findUserByUsername_ShouldReturnUserResponse() {

		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.of(userInitial));
		when(commonMapper.convertToResponse(userInitial, UserResponse.class)).thenReturn(userResponseExpected);

		UserResponse userResponseResult = userServiceImpl.findUserByUsername(userInitial.getUsername());

		assertThat(userResponseResult, is(userResponseExpected));

		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
		verify(commonMapper, times(1)).convertToResponse(userInitial, UserResponse.class);
	}

	@Test
	void findUserByUsername_ShouldUserNotFoundException_WhenUserNotFound() {

		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.empty());

		Exception exception = assertThrows(UserNotFoundException.class,
				() -> userServiceImpl.findUserByUsername(userInitial.getUsername()));
		assertThat(exception.getMessage(), is("User not found!"));

		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
	}

	// UnitTest for function findUserByUsernameReturnUserEntity()
	@Test
	void findUserByUsernameReturnUserEntity_ShouldReturnUserEntity() {

		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.of(userInitial));

		UserEntity userEntityResult = userServiceImpl.findUserByUsernameReturnUserEntity(userInitial.getUsername());

		assertThat(userEntityResult, is(userInitial));

		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
	}

	@Test
	void findUserByUsernameReturnUserEntity_ShouldUserNotFoundException_WhenUserNotFound() {

		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.empty());

		Exception exception = assertThrows(UserNotFoundException.class,
				() -> userServiceImpl.findUserByUsernameReturnUserEntity(userInitial.getUsername()));
		assertThat(exception.getMessage(), is("User not found!"));

		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
	}

	// UnitTest for function findAllUsers()
	@Test
	void findAllUsers_ShouldReturnAllUsers() {

		UserEntity userInitialSecond = UserEntity.builder().id(2L).firstName("firstName2").lastName("lastName2")
				.username("username2").password("password2").phoneNumber("phoneNumber2").address("address2")
				.isBlocked(false).roles((new HashSet<Role>(List.of(roleInitial)))).build();
		List<UserEntity> userEntityListInitial = new ArrayList<>();
		userEntityListInitial.add(userInitial);
		userEntityListInitial.add(userInitialSecond);

		UserResponse userResponseExpectedSecond = UserResponse.builder().id(2L).firstName("firstName2")
				.lastName("lastName2").username("username2").phoneNumber("phoneNumber2").address("address2")
				.isBlocked(false).roles((new HashSet<Role>(List.of(roleInitial)))).build();
		List<UserResponse> UserResponseListInitial = new ArrayList<>();
		UserResponseListInitial.add(userResponseExpected);
		UserResponseListInitial.add(userResponseExpectedSecond);

		when(userRepository.findAll()).thenReturn(userEntityListInitial);
		when(commonMapper.convertToResponseList(userEntityListInitial, UserResponse.class))
				.thenReturn(UserResponseListInitial);

		List<UserResponse> userResponseListResult = userServiceImpl.findAllUsers();

		assertThat(userResponseListResult, is(UserResponseListInitial));

		verify(userRepository, times(1)).findAll();
		verify(commonMapper, times(1)).convertToResponseList(userEntityListInitial, UserResponse.class);
	}

	// UnitTest for function updateProfile()
	@Test
	void updateProfile_ShouldReturnUpdatedUserResponse() {

		UserEntity userEntityUpdated = UserEntity.builder().id(1L).firstName("firstName1Updated")
				.lastName("lastName1Updated").username("username1").password("password1")
				.phoneNumber("phoneNumber1Updated").address("address1Updated").isBlocked(false)
				.roles((new HashSet<Role>(List.of(roleInitial)))).build();

		UserResponse userResponseUpdatedExpected = UserResponse.builder().id(1L).firstName("firstName1Updated")
				.lastName("lastName1Updated").username("username1").phoneNumber("phoneNumber1Updated")
				.address("address1Updated").isBlocked(false).roles((new HashSet<Role>(List.of(roleInitial)))).build();

		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.of(userInitial));
		when(userRepository.findByPhoneNumber(userRequestInitial.getPhoneNumber())).thenReturn(Optional.empty());
		when(userRepository.save(any())).thenReturn(userEntityUpdated);
		when(commonMapper.convertToResponse(userEntityUpdated, UserResponse.class))
				.thenReturn(userResponseUpdatedExpected);

		UserResponse userResponseUpdatedResult = userServiceImpl.updateProfile(userInitial.getUsername(),
				userRequestInitial);

		ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
		verify(userRepository).save(userCaptor.capture());
		UserEntity savedUserEntity = userCaptor.getValue();

		assertThat(savedUserEntity.getFirstName(), is(userEntityUpdated.getFirstName()));
		assertThat(savedUserEntity.getLastName(), is(userEntityUpdated.getLastName()));
		assertThat(savedUserEntity.getAddress(), is(userEntityUpdated.getAddress()));
		assertThat(savedUserEntity.getPhoneNumber(), is(userEntityUpdated.getPhoneNumber()));

		assertThat(userResponseUpdatedResult, is(userResponseUpdatedExpected));

		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
		verify(userRepository, times(1)).findByPhoneNumber(userRequestInitial.getPhoneNumber());
		verify(userRepository, times(1)).save(any());
		verify(commonMapper, times(1)).convertToResponse(userEntityUpdated, UserResponse.class);
	}

	@Test
	void updateProfile_ShouldThrowPhoneNumberExistException_WhenPhoneNumberAlreadyUse() {

		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.of(userInitial));
		when(userRepository.findByPhoneNumber(userRequestInitial.getPhoneNumber()))
				.thenReturn(Optional.of(userInitial));

		Exception exception = assertThrows(PhoneNumberExistException.class,
				() -> userServiceImpl.updateProfile(userInitial.getUsername(), userRequestInitial));
		assertThat(exception.getMessage(), is("Phone number is already used!"));

		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
		verify(userRepository, times(1)).findByPhoneNumber(userRequestInitial.getPhoneNumber());
	}

	@Test
	void updateProfile_ShouldThrowUserNotFoundException_WhenUserNotFound() {

		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.empty());

		Exception exception = assertThrows(UserNotFoundException.class,
				() -> userServiceImpl.updateProfile(userInitial.getUsername(), userRequestInitial));
		assertThat(exception.getMessage(), is("User not found!"));

		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
	}

}
