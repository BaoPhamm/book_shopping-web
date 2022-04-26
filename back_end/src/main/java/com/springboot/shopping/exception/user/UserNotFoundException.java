package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

	private final HttpStatus status;

	public UserNotFoundException() {
		super("User not found!");
		this.status = HttpStatus.NOT_FOUND;
	}
}
