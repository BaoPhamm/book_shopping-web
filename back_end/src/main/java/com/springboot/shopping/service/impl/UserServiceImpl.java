package com.springboot.shopping.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.shopping.exception.ApiRequestException;
import com.springboot.shopping.exception.PasswordConfirmationException;
import com.springboot.shopping.exception.RoleExistException;
import com.springboot.shopping.exception.UserNotFoundException;
import com.springboot.shopping.exception.UserRoleExistException;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userEntity = userRepository.findByUsername(username);
		if (!userEntity.isPresent()) {
			throw new UserNotFoundException();
		}
		Collection<SimpleGrantedAuthority> Authorities = new ArrayList<>();
		userEntity.get().getRoles().forEach(role -> Authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new User(userEntity.get().getUsername(), userEntity.get().getPassword(), Authorities);
	}

	@Override
	public Optional<UserEntity> findUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public Optional<UserEntity> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Role createRole(Role role) {
		Optional<Role> roleFromDb = roleRepository.findByname(role.getName());
		if (roleFromDb.isPresent()) {
			throw new RoleExistException("Role is already exist.");
		}
		return roleRepository.save(role);
	}

	@Override
	public UserEntity updateProfileUser(Long userId, UserEntity user) {
		UserEntity userFromDb = userRepository.findById(userId)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		userFromDb.setFirstName(user.getFirstName());
		userFromDb.setLastName(user.getLastName());
		userFromDb.setPhoneNumber(user.getPhoneNumber());
		userFromDb.setAddress(user.getAddress());
		return userRepository.save(userFromDb);
	}

	@Override
	public List<UserEntity> deleteUser(Long userId) {
		UserEntity userFromDb = userRepository.findById(userId)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		userRepository.deleteById(userId);
		return userRepository.findAll();
	}

	@Override
	public String addRoleToUser(String username, String roleName) {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		Role role = roleRepository.findByname(roleName)
				.orElseThrow(() -> new ApiRequestException("Role not found!", HttpStatus.NOT_FOUND));
		if (user.getRoles().contains(role)) {
			throw new UserRoleExistException("User already has this role !");
		}
		user.getRoles().add(role);
		userRepository.save(user);
		return "Role successfully added.";
	}

	@Override
	public UserEntity updateProfile(String username, UserEntity user) {
		UserEntity userFromDb = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiRequestException("Username not found.", HttpStatus.NOT_FOUND));
		userFromDb.setFirstName(user.getFirstName());
		userFromDb.setLastName(user.getLastName());
		userFromDb.setAddress(user.getAddress());
		userFromDb.setPhoneNumber(user.getPhoneNumber());
		userRepository.save(userFromDb);
		return userFromDb;
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
