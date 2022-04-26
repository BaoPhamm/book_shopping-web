package com.springboot.shopping.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	// Get book by ID
	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long BookId) {
		return ResponseEntity.ok(bookService.findBookById(BookId));
	}

	// Get all books
	@GetMapping()
	public ResponseEntity<List<BookResponse>> getAllBooks() {
		return ResponseEntity.ok(bookService.findAllBooks());
	}

	// Get books by category
	@GetMapping("/category")
	public ResponseEntity<List<BookResponse>> getBooksByCategory(@RequestParam String categoryName) {
		return ResponseEntity.ok(bookService.findBooksByCategory(categoryName));
	}

}