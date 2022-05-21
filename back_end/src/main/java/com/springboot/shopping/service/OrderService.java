package com.springboot.shopping.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.springboot.shopping.dto.order.OrderRequest;
import com.springboot.shopping.dto.order.OrderResponse;

public interface OrderService {

	List<OrderResponse> findAllOrders(Pageable pageable);

	List<OrderResponse> findOrderByUsername(String username);

	OrderResponse postOrder(OrderRequest orderRequest);

	String deleteOrder(Long orderId);
}
