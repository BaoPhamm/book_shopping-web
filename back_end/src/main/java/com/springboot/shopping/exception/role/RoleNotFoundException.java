package com.springboot.shopping.exception.role;

import lombok.Getter;

@Getter
public class RoleNotFoundException extends RuntimeException {

	public RoleNotFoundException() {
		super("Role not found!");
	}

	public RoleNotFoundException(Long roleId) {
		super("Role with id: " + roleId + " is not found!");
	}
}
