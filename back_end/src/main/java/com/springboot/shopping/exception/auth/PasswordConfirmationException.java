package com.springboot.shopping.exception.auth;

import lombok.Getter;

@Getter
public class PasswordConfirmationException extends RuntimeException {

	public PasswordConfirmationException(String passwordTwoError) {
		super(passwordTwoError);
	}
}
