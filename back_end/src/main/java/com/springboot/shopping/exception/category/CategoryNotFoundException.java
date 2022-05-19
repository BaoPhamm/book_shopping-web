package com.springboot.shopping.exception.category;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends RuntimeException {

	public CategoryNotFoundException() {
		super("Category is not found!");
	}

	public CategoryNotFoundException(Long categoryId) {
		super("Category with id: " + categoryId + " is not found!");
	}
}
