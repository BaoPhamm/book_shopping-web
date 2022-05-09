package com.springboot.shopping.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.shopping.dto.exception.ExceptionResponse;

@RestControllerAdvice
public class InputFieldException extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("ASASASASAS");
		String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
		System.out.println(errorMessage);
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), errorMessage,
				request.getDescription(false), HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
