package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class UserAlreadyBlockedException extends RuntimeException {

	public UserAlreadyBlockedException() {
		super("This user is already blocked.");
	}
}
