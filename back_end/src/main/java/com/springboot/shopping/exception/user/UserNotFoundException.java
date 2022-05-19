package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("User not found!");
	}
}
