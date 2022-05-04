package com.springboot.shopping.exception.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException() {
		super("Book is not found!");
	}

	public BookNotFoundException(Long bookId) {
		super("Book with id: " + bookId + " is not found!");
	}
}
