package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameExistException extends RuntimeException {

	public UsernameExistException() {
		super("UserName is already used!");
	}
}
