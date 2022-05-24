package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class RemoveDefaultRoleException extends RuntimeException {

	public RemoveDefaultRoleException() {
		super("Can not remove deafult role 'USER' from user.");
	}
}
