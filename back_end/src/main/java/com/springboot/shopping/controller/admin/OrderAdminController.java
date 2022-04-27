package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.order.OrderResponse;
import com.springboot.shopping.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class OrderAdminController {

	private final OrderService orderService;

	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponse>> getAllOrders() {
		List<OrderResponse> allOrders = orderService.findAllOrders();
		return ResponseEntity.ok(allOrders);
	}

	@PostMapping("/order")
	public ResponseEntity<List<OrderResponse>> getUserOrdersByUsername(@RequestBody String userName) {
		List<OrderResponse> orderList = orderService.findOrderByUsername(userName);
		return ResponseEntity.ok(orderList);
	}

	@DeleteMapping("/order/delete/{orderId}")
	public ResponseEntity<List<OrderResponse>> deleteOrder(@PathVariable("orderId") Long orderId) {
		List<OrderResponse> newOrderList = orderService.deleteOrder(orderId);
		return ResponseEntity.ok(newOrderList);
	}

}
