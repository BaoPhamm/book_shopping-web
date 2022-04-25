package com.springboot.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.book.AddCategoryToBookForm;
import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.dto.category.CategoryRequest;
import com.springboot.shopping.dto.category.CategoryResponse;
import com.springboot.shopping.dto.order.OrderResponse;
import com.springboot.shopping.dto.role.RoleRequest;
import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.AddRoleToUserForm;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.mapper.BookMapper;
import com.springboot.shopping.mapper.CategoryMapper;
import com.springboot.shopping.mapper.OrderMapper;
import com.springboot.shopping.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

	private final UserMapper userMapper;
	private final BookMapper bookMapper;
	private final OrderMapper orderMapper;
	private final CategoryMapper categoryMapper;

	@Value("${jwt.secret}")
	private String secretKey;

	// Get user by ID
	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long UserId) {
		return ResponseEntity.ok(userMapper.findUserById(UserId));
	}

	// Get All users
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(userMapper.findAllUsers());
	}

	// Delete an existing user by ID
	@DeleteMapping("/users/delete/{id}")
	public ResponseEntity<List<UserResponse>> deleteUser(@PathVariable("id") Long UserId) {
		return ResponseEntity.ok(userMapper.deleteUser(UserId));
	}

	@PostMapping("/users/create/role")
	public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
		return ResponseEntity.ok(userMapper.createRole(roleRequest));
	}

	@PostMapping("/users/role/addtouser")
	public ResponseEntity<String> addRoleToUser(@RequestBody AddRoleToUserForm addRoleToUserForm) {
		userMapper.addRoleToUser(addRoleToUserForm.getUsername(), addRoleToUserForm.getRolename());
		return ResponseEntity.ok().build();
	}

	// Create a new book
	@PostMapping("/books/create")
	public ResponseEntity<BookResponse> saveBook(@RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookMapper.createBook(bookRequest));
	}

	@PostMapping("/books/category/addtobook")
	public ResponseEntity<String> addCategoryToBook(@RequestBody AddCategoryToBookForm addCategoryToBookForm) {
		return ResponseEntity.ok(bookMapper.addCategoryToBook(addCategoryToBookForm.getBookTitle(),
				addCategoryToBookForm.getCategoryName()));
	}

	// Update an existing book
	@PutMapping("/books/update/{id}")
	public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long BookId,
			@RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookMapper.updateBook(BookId, bookRequest));
	}

	// Delete an existing book by ID
	@DeleteMapping("/books/delete/{id}")
	public ResponseEntity<List<BookResponse>> deleteBook(@PathVariable("id") Long BookId) {
		return ResponseEntity.ok(bookMapper.deleteBook(BookId));
	}

	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponse>> getAllOrders() {
		return ResponseEntity.ok(orderMapper.findAllOrders());
	}

	@PostMapping("/order")
	public ResponseEntity<List<OrderResponse>> getUserOrdersByUsername(@RequestBody String userName) {
		return ResponseEntity.ok(orderMapper.findOrderByUsername(userName));
	}

	@DeleteMapping("/order/delete/{orderId}")
	public ResponseEntity<List<OrderResponse>> deleteOrder(@PathVariable("orderId") Long orderId) {
		return ResponseEntity.ok(orderMapper.deleteOrder(orderId));
	}

	// Create a new category
	@PostMapping("/category/create")
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
		return ResponseEntity.ok(categoryMapper.createCategory(categoryRequest));
	}

	// Update an existing category
	@PutMapping("/category/update/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long categoryId,
			@RequestBody CategoryRequest categoryRequest) {
		return ResponseEntity.ok(categoryMapper.updateCategory(categoryId, categoryRequest));
	}

	// Delete an existing category by ID
	@DeleteMapping("/category/delete/{id}")
	public ResponseEntity<List<CategoryResponse>> deleteCategory(@PathVariable("id") Long categoryId) {
		return ResponseEntity.ok(categoryMapper.deleteCategory(categoryId));
	}

}
