package com.springboot.shopping.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double totalPrice;
	private LocalDate date;
	private String username;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;

	@OneToMany(fetch = FetchType.EAGER)
	private List<OrderItem> orderItems;

	public Order() {
		this.date = LocalDate.now();
		this.orderItems = new ArrayList<>();
	}

}
