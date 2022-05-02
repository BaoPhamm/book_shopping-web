package com.springboot.shopping.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PasswordException extends RuntimeException {

	public PasswordException(String message) {
		super(message);
	}
}
