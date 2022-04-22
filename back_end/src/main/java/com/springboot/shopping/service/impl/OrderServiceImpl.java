package com.springboot.shopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.shopping.exception.ApiRequestException;
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

	@Override
	public List<Order> findAll() {
		return orderRepository.findAllByOrderByIdAsc();
	}

	@Override
	public List<Order> findOrderByUsername(String username) {
		return orderRepository.findOrderByUsername(username);
	}

	@Override
	public Order postOrder(Order validOrder, Map<Long, Long> booksId) {
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
		return order;
	}

	@Override
	public List<Order> deleteOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ApiRequestException("Order not found.", HttpStatus.NOT_FOUND)); // TODO add test
		order.getOrderItems().forEach(orderItem -> orderItemRepository.deleteById(orderItem.getId()));
		orderRepository.delete(order);
		return orderRepository.findAllByOrderByIdAsc();
	}

}
