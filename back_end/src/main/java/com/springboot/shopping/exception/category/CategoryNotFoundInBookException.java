package com.springboot.shopping.exception.category;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CategoryNotFoundInBookException extends RuntimeException {

	private final HttpStatus status;

	public CategoryNotFoundInBookException() {
		super("Category is not found in this book!");
		this.status = HttpStatus.NOT_FOUND;
	}
}
