package com.springboot.shopping.exception;

import lombok.Getter;

@Getter
public class PasswordConfirmationException extends RuntimeException {

	private final String passwordTwoError;

	public PasswordConfirmationException(String passwordTwoError) {
		super();
		this.passwordTwoError = passwordTwoError;
	}

}
