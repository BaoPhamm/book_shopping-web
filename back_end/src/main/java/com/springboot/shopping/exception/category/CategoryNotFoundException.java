package com.springboot.shopping.exception.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

	public CategoryNotFoundException() {
		super("Category is not found!");
	}

	public CategoryNotFoundException(Long categoryId) {
		super("Category with id: " + categoryId + " is not found!");
	}
}
