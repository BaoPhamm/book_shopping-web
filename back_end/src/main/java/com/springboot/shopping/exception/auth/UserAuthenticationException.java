package com.springboot.shopping.exception.auth;

import lombok.Getter;

@Getter
public class UserAuthenticationException extends RuntimeException {

	public UserAuthenticationException(Throwable cause) {
		super(cause.getMessage());
	}
}
