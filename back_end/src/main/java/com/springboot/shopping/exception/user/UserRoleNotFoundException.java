package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserRoleNotFoundException extends RuntimeException {

	private final HttpStatus status;

	public UserRoleNotFoundException() {
		super("User don't has this role!");
		this.status = HttpStatus.NOT_FOUND;
	}
}
