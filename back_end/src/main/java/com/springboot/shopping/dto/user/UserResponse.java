package com.springboot.shopping.dto.user;

import java.util.Set;

import com.springboot.shopping.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String phoneNumber;
	private String address;
	private Set<Role> roles;
}