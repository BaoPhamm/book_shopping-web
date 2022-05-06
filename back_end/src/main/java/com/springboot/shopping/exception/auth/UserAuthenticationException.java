package com.springboot.shopping.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAuthenticationException extends RuntimeException {

	public UserAuthenticationException() {
		super("Incorrect password or username");
	}
}
