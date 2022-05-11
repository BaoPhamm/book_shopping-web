package com.springboot.shopping.exception.role;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleExistException extends RuntimeException {

	public RoleExistException() {
		super("Role is already exist!");
	}

	public RoleExistException(String roleName) {
		super("Role with name " + roleName + " is already exist!");
	}
}
