package com.springboot.shopping.exception.book;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BookExistException extends RuntimeException {

	private final HttpStatus status;

	public BookExistException() {
		super("Book is already existed!");
		this.status = HttpStatus.BAD_REQUEST;
	}
}
