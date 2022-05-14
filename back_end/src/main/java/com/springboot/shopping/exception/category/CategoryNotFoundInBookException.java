package com.springboot.shopping.exception.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundInBookException extends RuntimeException {


	public CategoryNotFoundInBookException() {
		super("Category is not found in this book!");
	}

	public CategoryNotFoundInBookException(Long categoryId) {
		super("Category " + categoryId + " is not found in this book!");
	}
}
