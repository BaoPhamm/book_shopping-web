package com.springboot.shopping.exception.role;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class RoleNotFoundException extends RuntimeException {

	private final HttpStatus status;

	public RoleNotFoundException() {
		super("Role not found!");
		this.status = HttpStatus.NOT_FOUND;
	}
}
