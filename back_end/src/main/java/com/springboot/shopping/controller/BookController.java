package com.springboot.shopping.controller;

import java.util.List;

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
import com.springboot.shopping.mapper.BookMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

	private final BookMapper bookMapper;

	// Get book by ID
	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long BookId) {
		return ResponseEntity.ok(bookMapper.findBookById(BookId));
	}

	// Get All books
	@GetMapping()
	public ResponseEntity<List<BookResponse>> getAllBooks() {
		return ResponseEntity.ok(bookMapper.findAllBooks());
	}

	// Create a new book
	@PostMapping("/create")
	public ResponseEntity<BookResponse> saveBook(@RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookMapper.createBook(bookRequest));
	}

	// Update an existing book
	@PutMapping("/update/{id}")
	public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long BookId,
			@RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookMapper.updateBook(BookId, bookRequest));
	}

	// Delete an existing book by ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<List<BookResponse>> deleteBook(@PathVariable("id") Long BookId) {
		return ResponseEntity.ok(bookMapper.deleteBook(BookId));
	}
}
