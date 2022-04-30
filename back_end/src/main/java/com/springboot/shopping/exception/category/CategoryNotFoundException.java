package com.springboot.shopping.exception.category;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends RuntimeException {

	private final HttpStatus status;

	public CategoryNotFoundException() {
		super("Category is not found!");
		this.status = HttpStatus.NOT_FOUND;
	}

	public CategoryNotFoundException(Long categoryId) {
		super("Category with id: "+categoryId+ " is not found!");
		this.status = HttpStatus.NOT_FOUND;
	}
}
