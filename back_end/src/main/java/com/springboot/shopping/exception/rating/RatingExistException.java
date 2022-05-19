package com.springboot.shopping.exception.rating;

import lombok.Getter;

@Getter
public class RatingExistException extends RuntimeException {

	public RatingExistException() {
		super("This user already rate this book.");
	}
}
