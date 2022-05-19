package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class UsernameExistException extends RuntimeException {

	public UsernameExistException() {
		super("UserName is already used!");
	}
}
