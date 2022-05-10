package com.springboot.shopping.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserRoleNotFoundException extends RuntimeException {

	public UserRoleNotFoundException() {
		super("User don't has this role!");
	}
}
