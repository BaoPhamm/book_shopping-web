package com.springboot.shopping.exception.book;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException {

	private final HttpStatus status;

	public BookNotFoundException() {
		super("Book is not found!");
		this.status = HttpStatus.NOT_FOUND;
	}

	public BookNotFoundException(Long bookId) {
		super("Book with id: " + bookId + " is not found!");
		this.status = HttpStatus.NOT_FOUND;
	}
}
