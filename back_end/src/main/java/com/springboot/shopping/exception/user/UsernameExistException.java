package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UsernameExistException extends RuntimeException {

	private final HttpStatus status;

	public UsernameExistException() {
		super("UserName is already used!");
		this.status = HttpStatus.BAD_REQUEST;
	}
}
