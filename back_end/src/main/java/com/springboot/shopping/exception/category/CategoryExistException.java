package com.springboot.shopping.exception.category;

import lombok.Getter;

@Getter
public class CategoryExistException extends RuntimeException {

	public CategoryExistException() {
		super("Category is already existed!");
	}

	public CategoryExistException(Long categoryId) {
		super("Category with id: " + categoryId + " is already existed in this book!");
	}
}
