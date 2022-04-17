package com.springboot.shopping.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

	private final String userError;

	public UserNotFoundException() {
		this.userError = "User not found!";
	}

}
