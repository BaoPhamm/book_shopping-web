package com.springboot.shopping.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.shopping.exception.ApiRequestException;
import com.springboot.shopping.exception.UserNotFoundException;
import com.springboot.shopping.exception.UserRoleExistException;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.User;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.repository.UserRepository;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		if (!user.isPresent()) {	
			throw new UserNotFoundException();
		}
		Collection<SimpleGrantedAuthority> Authorities = new ArrayList<>();
		user.get().getRoles().forEach(role -> Authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
				user.get().getPassword(), Authorities);
	}

	@Override
	public Optional<User> findUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public User updateProfileUser(Long userId, User user) {
		User userFromDb = userRepository.findById(userId)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		userFromDb.setFirstName(user.getFirstName());
		userFromDb.setLastName(user.getLastName());
		userFromDb.setPhoneNumber(user.getPhoneNumber());
		userFromDb.setAddress(user.getAddress());
		return userRepository.save(userFromDb);
	}

	@Override
	public List<User> deleteUser(Long userId) {
		User userFromDb = userRepository.findById(userId)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		userRepository.deleteById(userId);
		return userRepository.findAll();
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiRequestException("User not found!", HttpStatus.NOT_FOUND));
		Role role = roleRepository.findByname(roleName)
				.orElseThrow(() -> new ApiRequestException("Role not found!", HttpStatus.NOT_FOUND));
		if (user.getRoles().contains(role)) {
			throw new UserRoleExistException("User already has this role !");
		}
		user.getRoles().add(role);
		userRepository.save(user);
	}

}
