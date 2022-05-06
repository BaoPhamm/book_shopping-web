package com.springboot.shopping.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.order.OrderRequest;
import com.springboot.shopping.dto.order.OrderResponse;

public interface OrderService {

	List<OrderResponse> findAllOrders();

	List<OrderResponse> findOrderByUsername(String username);

	OrderResponse postOrder(OrderRequest orderRequest);

	List<OrderResponse> deleteOrder(Long orderId);
}
