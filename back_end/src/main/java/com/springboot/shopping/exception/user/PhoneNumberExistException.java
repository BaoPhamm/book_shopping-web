package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PhoneNumberExistException extends RuntimeException {

	private final HttpStatus status;

	public PhoneNumberExistException() {
		super("Phone number is already used!");
		this.status = HttpStatus.BAD_REQUEST;
	}
}
