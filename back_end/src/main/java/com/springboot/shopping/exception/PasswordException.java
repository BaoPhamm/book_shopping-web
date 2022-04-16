package com.springboot.shopping.exception;

import lombok.Getter;

@Getter
public class PasswordException extends RuntimeException {

	private final String passwordError;

	public PasswordException(String passwordError) {
		super();
		this.passwordError = passwordError;
	}

}
