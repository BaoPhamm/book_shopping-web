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
import com.springboot.shopping.dto.book.RemoveCategoryFromBookForm;
import com.springboot.shopping.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/books")
@RequiredArgsConstructor
public class BookAdminController {

	private final BookService bookService;

	// Create a new book
	@PostMapping()
	public ResponseEntity<BookResponse> saveBook(@RequestBody BookRequest bookRequest) {
		BookResponse createdBook = bookService.createBook(bookRequest);
		return ResponseEntity.ok(createdBook);
	}

	@PostMapping("/category/add-to-book")
	public ResponseEntity<String> addCategoriesToBook(@RequestBody AddCategoryToBookForm addCategoryToBookForm) {
		String message = bookService.addCategoriesToBook(addCategoryToBookForm.getBookId(),
				addCategoryToBookForm.getCategoriesId());
		return ResponseEntity.ok(message);
	}

	@PostMapping("/category/remove-from-book")
	public ResponseEntity<String> removeCategoriesFromBook(
			@RequestBody RemoveCategoryFromBookForm removeCategoryFromBookForm) {
		String message = bookService.removeCategoriesFromBook(removeCategoryFromBookForm.getBookId(),
				removeCategoryFromBookForm.getCategoriesId());
		return ResponseEntity.ok(message);
	}

	// Update an existing book
	@PutMapping()
	public ResponseEntity<BookResponse> updateBook(@RequestBody BookRequest bookRequest) {
		BookResponse updatedBook = bookService.updateBook(bookRequest);
		return ResponseEntity.ok(updatedBook);
	}

	// Delete an existing book by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<List<BookResponse>> deleteBook(@PathVariable("id") Long BookId) {
		List<BookResponse> newBookList = bookService.deleteBook(BookId);
		return ResponseEntity.ok(newBookList);
	}

}
