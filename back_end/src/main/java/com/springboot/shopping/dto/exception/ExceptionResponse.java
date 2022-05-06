package com.springboot.shopping.dto.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
	private Date date;
	private String errorMessage;
	private String requestDescription;
	private int httpStatus;

}
