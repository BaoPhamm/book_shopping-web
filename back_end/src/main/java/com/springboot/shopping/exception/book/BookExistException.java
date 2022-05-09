package com.springboot.shopping.exception.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookExistException extends RuntimeException {

	public BookExistException() {
		super("Book is already existed!");
	}
}
