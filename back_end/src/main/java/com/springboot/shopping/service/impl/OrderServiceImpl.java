package com.springboot.shopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.order.OrderRequest;
import com.springboot.shopping.dto.order.OrderResponse;
import com.springboot.shopping.exception.ApiRequestException;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Book;
import com.springboot.shopping.model.Order;
import com.springboot.shopping.model.OrderItem;
import com.springboot.shopping.repository.BookRepository;
import com.springboot.shopping.repository.OrderItemRepository;
import com.springboot.shopping.repository.OrderRepository;
import com.springboot.shopping.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final BookRepository bookRepository;
	private final CommonMapper commonMapper;

	@Override
	public List<OrderResponse> findAllOrders() {
		return commonMapper.convertToResponseList(orderRepository.findAllByOrderByIdAsc(), OrderResponse.class);
	}

	@Override
	public List<OrderResponse> findOrderByUsername(String username) {
		return commonMapper.convertToResponseList(orderRepository.findOrderByUsername(username), OrderResponse.class);
	}

	@Override
	public OrderResponse postOrder(OrderRequest orderRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		}

		Order validOrder = commonMapper.convertToEntity(orderRequest, Order.class);
		Map<Long, Long> booksId = orderRequest.getBooksId();

		Order order = new Order();
		List<OrderItem> orderItemList = new ArrayList<>();

		for (Map.Entry<Long, Long> entry : booksId.entrySet()) {
			Book book = bookRepository.findById(entry.getKey()).get();
			OrderItem orderItem = new OrderItem();
			orderItem.setBook(book);
			orderItem.setAmount((book.getPrice() * entry.getValue()));
			orderItem.setQuantity(entry.getValue());
			orderItemList.add(orderItem);
			orderItemRepository.save(orderItem);
		}
		order.getOrderItems().addAll(orderItemList);
		order.setTotalPrice(validOrder.getTotalPrice());
		order.setFirstName(validOrder.getFirstName());
		order.setLastName(validOrder.getLastName());
		order.setAddress(validOrder.getAddress());
		order.setPhoneNumber(validOrder.getPhoneNumber());
		orderRepository.save(order);
		return commonMapper.convertToResponse(order, OrderResponse.class);
	}

	@Override
	public List<OrderResponse> deleteOrder(Long orderId) {

		Optional<Order> orderFromDb = orderRepository.findById(orderId);
		if (orderFromDb.isEmpty()) {
			throw new ApiRequestException("Order not found.", HttpStatus.NOT_FOUND);
		}
		orderFromDb.get().getOrderItems().forEach(orderItem -> orderItemRepository.deleteById(orderItem.getId()));
		orderRepository.delete(orderFromDb.get());
		return commonMapper.convertToResponseList(orderRepository.findAllByOrderByIdAsc(), OrderResponse.class);
	}

}
