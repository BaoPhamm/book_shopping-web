package com.springboot.shopping.exception.user;

import lombok.Getter;

@Getter
public class PhoneNumberExistException extends RuntimeException {

	public PhoneNumberExistException() {
		super("Phone number is already used!");
	}
}
