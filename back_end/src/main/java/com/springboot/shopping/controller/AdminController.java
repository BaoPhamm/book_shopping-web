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

import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.dto.role.RoleRequest;
import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.dto.user.AddRoleToUserForm;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.mapper.BookMapper;
import com.springboot.shopping.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

	private final UserMapper userMapper;
	private final BookMapper bookMapper;

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
	public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserForm addRoleToUserForm) {
		userMapper.addRoleToUser(addRoleToUserForm.getUsername(), addRoleToUserForm.getRolename());
		return ResponseEntity.ok().build();
	}

	// Create a new book
	@PostMapping("/books/create")
	public ResponseEntity<BookResponse> saveBook(@RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookMapper.createBook(bookRequest));
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

}
