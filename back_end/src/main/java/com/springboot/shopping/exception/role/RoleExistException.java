package com.springboot.shopping.exception.role;

import lombok.Getter;

@Getter
public class RoleExistException extends RuntimeException {

	public RoleExistException() {
		super("Role is already exist!");
	}

	public RoleExistException(String roleName) {
		super("Role with name " + roleName + " is already exist!");
	}
}
