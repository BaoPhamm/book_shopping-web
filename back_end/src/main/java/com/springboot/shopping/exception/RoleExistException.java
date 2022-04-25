package com.springboot.shopping.exception;

import lombok.Getter;

@Getter
public class RoleExistException extends RuntimeException {

	private final String roleError;

	public RoleExistException(String roleError) {
		this.roleError = roleError;
	}

}
