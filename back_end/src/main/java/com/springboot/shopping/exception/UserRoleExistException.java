package com.springboot.shopping.exception;

import lombok.Getter;

@Getter
public class UserRoleExistException extends RuntimeException {

	private final String userError;

	public UserRoleExistException(String userError) {
		this.userError = userError;
	}

}
