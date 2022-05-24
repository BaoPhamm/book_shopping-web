package com.springboot.shopping.exception.role;

import lombok.Getter;

@Getter
public class DeleteDefaultRoleException extends RuntimeException {

	public DeleteDefaultRoleException(String roleName) {
		super("Can not delete default role: " + roleName);
	}
}
