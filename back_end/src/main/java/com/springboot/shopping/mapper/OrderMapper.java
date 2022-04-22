package com.springboot.shopping.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.order.OrderRequest;
import com.springboot.shopping.dto.order.OrderResponse;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.model.Order;
import com.springboot.shopping.service.OrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderMapper {

	private final CommonMapper commonMapper;
	private final OrderService orderService;

	public List<OrderResponse> findAllOrders() {
		return commonMapper.convertToResponseList(orderService.findAll(), OrderResponse.class);
	}

	public List<OrderResponse> findOrderByUsername(String username) {
		return commonMapper.convertToResponseList(orderService.findOrderByUsername(username), OrderResponse.class);
	}

	public List<OrderResponse> deleteOrder(Long orderId) {
		return commonMapper.convertToResponseList(orderService.deleteOrder(orderId), OrderResponse.class);
	}

	public OrderResponse postOrder(OrderRequest orderRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		}
		Order order = orderService.postOrder(commonMapper.convertToEntity(orderRequest, Order.class),
				orderRequest.getBooksId());
		return commonMapper.convertToResponse(order, OrderResponse.class);
	}

}
