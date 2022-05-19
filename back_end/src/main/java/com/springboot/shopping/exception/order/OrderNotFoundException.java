package com.springboot.shopping.exception.order;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException() {
		super("Order is not found!");
	}
}
