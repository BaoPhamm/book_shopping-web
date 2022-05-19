package com.springboot.shopping.exception.book;

import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException() {
		super("Book is not found!");
	}

	public BookNotFoundException(Long bookId) {
		super("Book with id: " + bookId + " is not found!");
	}
}
