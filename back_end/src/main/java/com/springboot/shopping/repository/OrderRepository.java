package com.springboot.shopping.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.shopping.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findAllByOrderByIdAsc(Pageable pageable);

	List<Order> findOrderByUsername(String username);
}
