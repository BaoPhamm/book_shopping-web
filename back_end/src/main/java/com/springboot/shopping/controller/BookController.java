package com.springboot.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.springboot.shopping.mapper.BookMapper;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class BookController {

	private final BookMapper bookMapper;

	public BookController(BookMapper bookMapper) {
		super();
		this.bookMapper = bookMapper;
	}

	// Get book by ID
	@GetMapping("/books/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long BookId) {
		return ResponseEntity.ok(bookMapper.findBookById(BookId));
	}

	// Get All books
	@GetMapping("/books")
	public ResponseEntity<List<BookResponse>> getAllBooks() {
		return ResponseEntity.ok(bookMapper.findAllBooks());
	}

	// Create a new book
	@PostMapping("/books/create")
	public ResponseEntity<BookResponse> saveBook(@RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookMapper.createBook(bookRequest));
	}

	// Update an existing book
	@PutMapping("/books/update/{id}")
	public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long BookId, @RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookMapper.updateBook(BookId, bookRequest));
	}

	// Delete an existing book by ID
	@DeleteMapping("/books/delete/{id}")
	public ResponseEntity<List<BookResponse>> deleteBook(@PathVariable("id") Long BookId) {
		return ResponseEntity.ok(bookMapper.deleteBook(BookId));
	}
}
