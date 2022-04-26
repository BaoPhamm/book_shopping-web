package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.book.AddCategoryToBookForm;
import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class BookAdminController {

	private final BookService bookService;

	// Create a new book
	@PostMapping("/books/create")
	public ResponseEntity<BookResponse> saveBook(@RequestBody BookRequest bookRequest) {
		return ResponseEntity.ok(bookService.createBook(bookRequest));
	}

	@PostMapping("/books/category/add-to-book")
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

}
