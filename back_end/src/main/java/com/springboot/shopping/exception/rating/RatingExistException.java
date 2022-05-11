package com.springboot.shopping.exception.rating;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RatingExistException extends RuntimeException {

	public RatingExistException() {
		super("This user already rate this book.");
	}
}
