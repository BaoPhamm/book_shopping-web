package com.springboot.shopping.dto.order;

import com.springboot.shopping.dto.book.BookResponse;

import lombok.Data;

@Data
public class OrderItemResponse {
	private Long id;
	private Long amount;
	private Long quantity;
	private BookResponse book;
}
