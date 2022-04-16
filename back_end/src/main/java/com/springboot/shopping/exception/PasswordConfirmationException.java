package com.springboot.shopping.exception;

import lombok.Getter;

@Getter
public class PasswordConfirmationException extends RuntimeException {

	private final String password2Error;

	public PasswordConfirmationException(String password2Error) {
		super();
		this.password2Error = password2Error;
	}

}
