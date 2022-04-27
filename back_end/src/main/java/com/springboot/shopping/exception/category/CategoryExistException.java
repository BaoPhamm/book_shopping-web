package com.springboot.shopping.exception.category;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CategoryExistException extends RuntimeException {

	private final HttpStatus status;

	public CategoryExistException() {
		super("Category is already existed!");
		this.status = HttpStatus.BAD_REQUEST;
	}
	
	public CategoryExistException(String categoryName) {
		super("Category: "+categoryName+" is already existed in this book!");
		this.status = HttpStatus.BAD_REQUEST;
	}
}
