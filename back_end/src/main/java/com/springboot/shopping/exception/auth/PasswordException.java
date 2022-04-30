package com.springboot.shopping.exception.auth;

import lombok.Getter;

@Getter
public class PasswordException extends RuntimeException {

	public PasswordException(String message) {
		super(message);
	}
}
