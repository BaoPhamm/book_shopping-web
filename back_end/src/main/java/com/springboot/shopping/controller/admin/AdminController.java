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
import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.AddRoleToUserForm;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.service.BookService;
import com.springboot.shopping.service.CategoryService;
import com.springboot.shopping.service.OrderService;
import com.springboot.shopping.service.RoleService;
import com.springboot.shopping.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;
	private final BookService bookService;
	private final OrderService orderService;
	private final CategoryService categoryService;

	@Value("${jwt.secret}")
	private String secretKey;

	// Get user by ID
	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long UserId) {
		return ResponseEntity.ok(userService.findUserById(UserId));
	}

	// Get All users
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return ResponseEntity.ok(userService.findAllUsers());
	}

	// Delete an existing user by ID
	@DeleteMapping("/users/delete/{id}")
	public ResponseEntity<List<UserResponse>> deleteUser(@PathVariable("id") Long UserId) {
		return ResponseEntity.ok(userService.deleteUser(UserId));
	}

	@PostMapping("/role/create")
	public ResponseEntity<RoleResponse> createRole(@RequestBody String roleName) {
		return ResponseEntity.ok(roleService.createRole(roleName));
	}

	@PostMapping("/users/role/addtouser")
	public ResponseEntity<String> addRoleToUser(@RequestBody AddRoleToUserForm addRoleToUserForm) {
		userService.addRoleToUser(addRoleToUserForm.getUsername(), addRoleToUserForm.getRolename());
		return ResponseEntity.ok().build();
	}

	// Create a new book
	@PostMapping("/books/create")
	public ResponseEntity<BookResponse> saveBook(@RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookService.createBook(bookRequest));
	}

	@PostMapping("/books/category/addtobook")
	public ResponseEntity<String> addCategoryToBook(@RequestBody AddCategoryToBookForm addCategoryToBookForm) {
		return ResponseEntity.ok(bookService.addCategoryToBook(addCategoryToBookForm.getBookTitle(),
				addCategoryToBookForm.getCategoryName()));
	}

	// Update an existing book
	@PutMapping("/books/update/{id}")
	public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long BookId,
			@RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookService.updateBook(BookId, bookRequest));
	}

	// Delete an existing book by ID
	@DeleteMapping("/books/delete/{id}")
	public ResponseEntity<List<BookResponse>> deleteBook(@PathVariable("id") Long BookId) {
		return ResponseEntity.ok(bookService.deleteBook(BookId));
	}

	@GetMapping("/orders")
	public ResponseEntity<List<OrderResponse>> getAllOrders() {
		return ResponseEntity.ok(orderService.findAllOrders());
	}

	@PostMapping("/order")
	public ResponseEntity<List<OrderResponse>> getUserOrdersByUsername(@RequestBody String userName) {
		return ResponseEntity.ok(orderService.findOrderByUsername(userName));
	}

	@DeleteMapping("/order/delete/{orderId}")
	public ResponseEntity<List<OrderResponse>> deleteOrder(@PathVariable("orderId") Long orderId) {
		return ResponseEntity.ok(orderService.deleteOrder(orderId));
	}

	// Create a new category
	@PostMapping("/category/create")
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
		return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
	}

	// Update an existing category
	@PutMapping("/category/update/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long categoryId,
			@RequestBody CategoryRequest categoryRequest) {
		return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryRequest));
	}

	// Delete an existing category by ID
	@DeleteMapping("/category/delete/{id}")
	public ResponseEntity<List<CategoryResponse>> deleteCategory(@PathVariable("id") Long categoryId) {
		return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
	}

}
