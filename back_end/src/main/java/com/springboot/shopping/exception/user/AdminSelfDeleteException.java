package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class AdminSelfDeleteException extends RuntimeException {

	public AdminSelfDeleteException() {
		super("You can not delete yourself.");
	}
}
