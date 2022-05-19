package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class AdminSelfBlockException extends RuntimeException {

	public AdminSelfBlockException() {
		super("You can not block yourself.");
	}
}
