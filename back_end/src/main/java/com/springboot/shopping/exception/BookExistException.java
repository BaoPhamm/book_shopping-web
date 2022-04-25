package com.springboot.shopping.exception;

import lombok.Getter;

@Getter
public class BookExistException extends RuntimeException {

	private final String bookError;

	public BookExistException(String bookError) {
		this.bookError = bookError;
	}

}
