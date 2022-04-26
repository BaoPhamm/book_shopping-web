package com.springboot.shopping.exception.auth;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UserAuthenticationException extends RuntimeException {

	private final HttpStatus status;

	public UserAuthenticationException() {
		super("Incorrect password or username");
		this.status = HttpStatus.FORBIDDEN;
	}
}
