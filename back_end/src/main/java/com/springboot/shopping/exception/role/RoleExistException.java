package com.springboot.shopping.exception.role;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class RoleExistException extends RuntimeException {

	private final HttpStatus status;

	public RoleExistException() {
		super("Role is already exist!");
		this.status = HttpStatus.BAD_REQUEST;
	}
}
