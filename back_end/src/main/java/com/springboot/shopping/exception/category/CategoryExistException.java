package com.springboot.shopping.exception.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryExistException extends RuntimeException {

	public CategoryExistException() {
		super("Category is already existed!");
	}

	public CategoryExistException(Long categoryId) {
		super("Category with id: " + categoryId + " is already existed in this book!");
	}
}
