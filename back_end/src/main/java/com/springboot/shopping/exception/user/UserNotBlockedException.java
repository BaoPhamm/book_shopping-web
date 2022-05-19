package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class UserNotBlockedException extends RuntimeException {

	public UserNotBlockedException() {
		super("This user is not blocked.");
	}
}
