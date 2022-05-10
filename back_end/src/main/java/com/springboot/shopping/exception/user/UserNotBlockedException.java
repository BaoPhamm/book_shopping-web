package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class UserNotBlockedException extends RuntimeException {

	public UserNotBlockedException() {
		super("This user is not blocked.");
	}
}
