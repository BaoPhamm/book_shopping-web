package com.springboot.shopping.exception.book;

import lombok.Getter;

@Getter
public class BookExistException extends RuntimeException {

	public BookExistException() {
		super("Book is already existed!");
	}

	public BookExistException(String bookTitle) {
		super("Title \"" + bookTitle + "\" is already existed!");
	}
}
