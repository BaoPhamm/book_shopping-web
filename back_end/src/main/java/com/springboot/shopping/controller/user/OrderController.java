package com.springboot.shopping.controller.user;

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
@RequestMapping("/api/v1/user/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping()
	public ResponseEntity<List<OrderResponse>> getUserOrders() {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<OrderResponse> orderList = orderService.findOrderByUsername(username);
		return ResponseEntity.ok(orderList);
	}

	@PostMapping()
	public ResponseEntity<OrderResponse> postOrder(@Valid @RequestBody OrderRequest order,
			BindingResult bindingResult) {
		OrderResponse orderResponse = orderService.postOrder(order, bindingResult);
		return ResponseEntity.ok(orderResponse);
	}

}
