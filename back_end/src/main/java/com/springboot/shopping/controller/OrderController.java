package com.springboot.shopping.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.order.OrderRequest;
import com.springboot.shopping.dto.order.OrderResponse;
import com.springboot.shopping.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponse>> getUserOrders() {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(orderService.findOrderByUsername(username));
	}

	@PostMapping("/order")
	public ResponseEntity<OrderResponse> postOrder(@Valid @RequestBody OrderRequest order,
			BindingResult bindingResult) {
		return ResponseEntity.ok(orderService.postOrder(order, bindingResult));
	}

}
