package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserRoleExistException extends RuntimeException {

	private final HttpStatus status;

	public UserRoleExistException() {
		super("User already has this role!");
		this.status = HttpStatus.NOT_FOUND;
	}
}
