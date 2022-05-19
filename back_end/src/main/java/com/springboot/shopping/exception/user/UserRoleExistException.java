package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class UserRoleExistException extends RuntimeException {

	public UserRoleExistException() {
		super("User already has this role!");
	}

	public UserRoleExistException(Long RoleId) {
		super("Role with id: " + RoleId + " is already existed in this user!");
	}
}
