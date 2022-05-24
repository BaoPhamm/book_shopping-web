package com.springboot.shopping.exception.role;

import lombok.Getter;

@Getter
public class UpdateDefaultRoleException extends RuntimeException {

	public UpdateDefaultRoleException(String roleName) {
		super("Can not update default role: " + roleName);
	}
}
