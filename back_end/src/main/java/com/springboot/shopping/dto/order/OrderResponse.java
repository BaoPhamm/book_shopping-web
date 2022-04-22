package com.springboot.shopping.dto.order;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponse {
	private long id;
	private Double totalPrice;
	private LocalDate date;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private List<OrderItemResponse> orderItems;
}
