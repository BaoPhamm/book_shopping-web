package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class UserAlreadyBlockedException extends RuntimeException {

	public UserAlreadyBlockedException() {
		super("This user is already blocked.");
	}
}
