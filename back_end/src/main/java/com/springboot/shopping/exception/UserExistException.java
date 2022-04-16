package com.springboot.shopping.exception;

import lombok.Getter;

@Getter
public class UserExistException extends RuntimeException {

	private final String userError;

	public UserExistException(String userError) {
		this.userError = userError;
	}

}
