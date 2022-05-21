package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.order.OrderResponse;
import com.springboot.shopping.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/order")
@RequiredArgsConstructor
public class OrderAdminController {

	private final OrderService orderService;

	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponse>> getAllOrders(
			@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "20") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		List<OrderResponse> allOrders = orderService.findAllOrders(pageable);
		return ResponseEntity.ok(allOrders);
	}

	@GetMapping("/{userName}")
	public ResponseEntity<List<OrderResponse>> getUserOrdersByUsername(@PathVariable("userName") String userName) {
		List<OrderResponse> orderList = orderService.findOrderByUsername(userName);
		return ResponseEntity.ok(orderList);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId) {
		String message = orderService.deleteOrder(orderId);
		return ResponseEntity.ok(message);
	}

}
