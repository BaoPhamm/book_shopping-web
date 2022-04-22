package com.springboot.shopping.dto.order;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class OrderRequest {

	private Double totalPrice;
	private Map<Long, Long> booksId;

	@NotBlank(message = "Fill in the input field")
	private String firstName;

	@NotBlank(message = "Fill in the input field")
	private String lastName;

	@NotBlank(message = "Fill in the input field")
	private String address;

	@NotBlank(message = "Phone number cannot be empty")
	private String phoneNumber;
}
