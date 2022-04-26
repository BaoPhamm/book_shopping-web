package com.springboot.shopping.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {

	private final HttpStatus status;

	public OrderNotFoundException() {
		super("Order is not found!");
		this.status = HttpStatus.NOT_FOUND;
	}
}
