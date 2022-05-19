package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class UserRoleNotFoundException extends RuntimeException {

	public UserRoleNotFoundException() {
		super("User don't has this role!");
	}
}
