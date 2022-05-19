package com.springboot.shopping.exception.category;

import lombok.Getter;

@Getter
public class CategoryNotFoundInBookException extends RuntimeException {

	public CategoryNotFoundInBookException() {
		super("Category is not found in this book!");
	}

	public CategoryNotFoundInBookException(Long categoryId) {
		super("Category " + categoryId + " is not found in this book!");
	}
}
