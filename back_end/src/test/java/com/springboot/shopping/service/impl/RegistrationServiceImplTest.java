package com.springboot.shopping.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.exception.auth.PasswordException;
import com.springboot.shopping.exception.user.PhoneNumberExistException;
import com.springboot.shopping.exception.user.UsernameExistException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;

class RegistrationServiceImplTest {

	private RegistrationServiceImpl registrationServiceImpl;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private CommonMapper commonMapper;

	private RegistrationRequest registrationRequestInitial;
	private UserEntity userInitial;
	private UserEntity savedUserExpected;
	private Role roleInitial;

	@BeforeEach
	void beforeEach() {
		// Mock UserRepository
		userRepository = mock(UserRepository.class);
		// Mock RoleRepository
		roleRepository = mock(RoleRepository.class);
		// Mock PasswordEncoder
		passwordEncoder = mock(PasswordEncoder.class);
		// Mock CommonMapper
		commonMapper = mock(CommonMapper.class);

		registrationServiceImpl = new RegistrationServiceImpl(userRepository, roleRepository, passwordEncoder,
				commonMapper);

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
	}

	// UnitTest for function registerUser()
	@Test
	void registerUser_ShouldReturnSuccessMessage() {

		when(commonMapper.convertToEntity(registrationRequestInitial, UserEntity.class)).thenReturn(userInitial);
		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.empty());
		when(userRepository.findByPhoneNumber(userInitial.getPhoneNumber())).thenReturn(Optional.empty());
		when(roleRepository.findByname(roleInitial.getName())).thenReturn(Optional.of(roleInitial));
		when(passwordEncoder.encode(userInitial.getPassword())).thenReturn(userInitial.getPassword() + "Encoded");

		String messageResult = registrationServiceImpl.registerUser(registrationRequestInitial);

		ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
		verify(userRepository).save(userCaptor.capture());
		UserEntity savedUser = userCaptor.getValue();

		assertThat(savedUser.equals(savedUserExpected), is(true));
		assertThat(messageResult, is("User successfully registered."));

		verify(commonMapper, times(1)).convertToEntity(registrationRequestInitial, UserEntity.class);
		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
		verify(userRepository, times(1)).findByPhoneNumber(userInitial.getPhoneNumber());
		verify(roleRepository, times(1)).findByname(roleInitial.getName());
		verify(passwordEncoder, times(1)).encode(registrationRequestInitial.getPassword());
	}

	@Test
	void registerUser_ShouldThrowPasswordException_WhenPasswordsNotMatch() {

		registrationRequestInitial.setPasswordRepeat(registrationRequestInitial.getPassword() + "Repeat");

		when(commonMapper.convertToEntity(registrationRequestInitial, UserEntity.class)).thenReturn(userInitial);
		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.empty());
		when(userRepository.findByPhoneNumber(userInitial.getPhoneNumber())).thenReturn(Optional.empty());

		Exception exception = assertThrows(PasswordException.class,
				() -> registrationServiceImpl.registerUser(registrationRequestInitial));
		assertThat(exception.getMessage(), is("Passwords do not match."));

		verify(commonMapper, times(1)).convertToEntity(registrationRequestInitial, UserEntity.class);
		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
		verify(userRepository, times(1)).findByPhoneNumber(userInitial.getPhoneNumber());
	}

	@Test
	void registerUser_ShouldThrowPasswordException_WhenPasswordIsBlank() {

		userInitial.setPassword("");

		when(commonMapper.convertToEntity(registrationRequestInitial, UserEntity.class)).thenReturn(userInitial);
		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.empty());
		when(userRepository.findByPhoneNumber(userInitial.getPhoneNumber())).thenReturn(Optional.empty());

		Exception exception = assertThrows(PasswordException.class,
				() -> registrationServiceImpl.registerUser(registrationRequestInitial));
		assertThat(exception.getMessage(), is("Password can not be blank."));

		verify(commonMapper, times(1)).convertToEntity(registrationRequestInitial, UserEntity.class);
		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
		verify(userRepository, times(1)).findByPhoneNumber(userInitial.getPhoneNumber());
	}

	@Test
	void registerUser_ShouldThrowPhoneNumberExistException_WhenPhoneNumberAlreadyExist() {

		when(commonMapper.convertToEntity(registrationRequestInitial, UserEntity.class)).thenReturn(userInitial);
		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.empty());
		when(userRepository.findByPhoneNumber(userInitial.getPhoneNumber())).thenReturn(Optional.of(userInitial));

		Exception exception = assertThrows(PhoneNumberExistException.class,
				() -> registrationServiceImpl.registerUser(registrationRequestInitial));
		assertThat(exception.getMessage(), is("Phone number is already used!"));

		verify(commonMapper, times(1)).convertToEntity(registrationRequestInitial, UserEntity.class);
		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
		verify(userRepository, times(1)).findByPhoneNumber(userInitial.getPhoneNumber());
	}

	@Test
	void registerUser_ShouldThrowUsernameExistException_WhenUsernameAlreadyExist() {

		when(commonMapper.convertToEntity(registrationRequestInitial, UserEntity.class)).thenReturn(userInitial);
		when(userRepository.findByUsername(userInitial.getUsername())).thenReturn(Optional.of(userInitial));

		Exception exception = assertThrows(UsernameExistException.class,
				() -> registrationServiceImpl.registerUser(registrationRequestInitial));
		assertThat(exception.getMessage(), is("UserName is already used!"));

		verify(commonMapper, times(1)).convertToEntity(registrationRequestInitial, UserEntity.class);
		verify(userRepository, times(1)).findByUsername(userInitial.getUsername());
	}

}
