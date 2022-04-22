package com.springboot.shopping.service;

import java.util.List;
import java.util.Map;

import com.springboot.shopping.model.Order;

public interface OrderService {

	List<Order> findAll();

	List<Order> findOrderByUsername(String username);

	Order postOrder(Order validOrder, Map<Long, Long> booksId);

	List<Order> deleteOrder(Long orderId);
}
