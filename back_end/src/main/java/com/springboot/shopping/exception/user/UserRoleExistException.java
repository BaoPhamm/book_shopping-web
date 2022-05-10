package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserRoleExistException extends RuntimeException {

	public UserRoleExistException() {
		super("User already has this role!");
	}

	public UserRoleExistException(Long RoleId) {
		super("Role with id: " + RoleId + " is already existed in this user!");
	}
}
