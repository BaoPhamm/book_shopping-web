package com.springboot.shopping.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.service.book.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	// Get book by ID
	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long bookId) {
		BookResponse bookResponse = bookService.findBookById(bookId);
		return ResponseEntity.ok(bookResponse);
	}

	// Get all books
	@GetMapping()
	public ResponseEntity<List<BookResponse>> getAllBooks() {
		List<BookResponse> allBooks = bookService.findAllBooks();
		return ResponseEntity.ok(allBooks);
	}
	
	// Get features books
	@GetMapping("/features")
	public ResponseEntity<List<BookResponse>> getFeaturesBooks() {
		List<BookResponse> featuresBooks = bookService.findFeaturesBooks();
		return ResponseEntity.ok(featuresBooks);
	}

	// Get books by category
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<BookResponse>> getBooksByCategory(@PathVariable("categoryId") Long categoryId) {
		List<BookResponse> bookList = bookService.findBooksByCategory(categoryId);
		return ResponseEntity.ok(bookList);
	}
	
	// Get book rating by ID
	@GetMapping("/{id}/rating")
	public ResponseEntity<Float> getBookRatingById(@PathVariable("id") Long bookId) {
		Float bookRating = bookService.getBookRatingById(bookId);
		return ResponseEntity.ok(bookRating);
	}
}
