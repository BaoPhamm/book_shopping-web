package com.springboot.shopping.controller.admin;

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

import com.springboot.shopping.dto.book.AddCategoryToBookForm;
import com.springboot.shopping.dto.book.BookAdminResponse;
import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.RemoveCategoryFromBookForm;
import com.springboot.shopping.service.book.BookAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/books")
@RequiredArgsConstructor
public class BookAdminController {

	private final BookAdminService bookAdminService;

	// Get book by ID
	@GetMapping("/{id}")
	public ResponseEntity<BookAdminResponse> getBookById(@PathVariable("id") Long bookId) {
		BookAdminResponse bookResponse = bookAdminService.findBookById(bookId);
		return ResponseEntity.ok(bookResponse);
	}

	// Get all books
	@GetMapping()
	public ResponseEntity<List<BookAdminResponse>> getAllBooks() {
		List<BookAdminResponse> allBooks = bookAdminService.findAllBooks();
		return ResponseEntity.ok(allBooks);
	}

	// Get features books
	@GetMapping("/features")
	public ResponseEntity<List<BookAdminResponse>> getFeaturesBooks() {
		List<BookAdminResponse> featuresBooks = bookAdminService.findFeaturesBooks();
		return ResponseEntity.ok(featuresBooks);
	}

	// Get books by category
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<BookAdminResponse>> getBooksByCategory(@PathVariable("categoryId") Long categoryId) {
		List<BookAdminResponse> bookList = bookAdminService.findBooksByCategory(categoryId);
		return ResponseEntity.ok(bookList);
	}

	// Create a new book
	@PostMapping()
	public ResponseEntity<BookAdminResponse> saveBook(@RequestBody BookRequest bookRequest) {
		BookAdminResponse createdBook = bookAdminService.createBook(bookRequest);
		return ResponseEntity.ok(createdBook);
	}

	@PostMapping("/category/add-to-book")
	public ResponseEntity<String> addCategoriesToBook(@RequestBody AddCategoryToBookForm addCategoryToBookForm) {
		String message = bookAdminService.addCategoriesToBook(addCategoryToBookForm.getBookId(),
				addCategoryToBookForm.getCategoriesId());
		return ResponseEntity.ok(message);
	}

	@PostMapping("/category/remove-from-book")
	public ResponseEntity<String> removeCategoriesFromBook(
			@RequestBody RemoveCategoryFromBookForm removeCategoryFromBookForm) {
		String message = bookAdminService.removeCategoriesFromBook(removeCategoryFromBookForm.getBookId(),
				removeCategoryFromBookForm.getCategoriesId());
		return ResponseEntity.ok(message);
	}

	// Update an existing book
	@PutMapping()
	public ResponseEntity<BookAdminResponse> updateBook(@RequestBody BookRequest bookRequest) {
		BookAdminResponse updatedBook = bookAdminService.updateBook(bookRequest);
		return ResponseEntity.ok(updatedBook);
	}

	// Delete an existing book by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<List<BookAdminResponse>> deleteBook(@PathVariable("id") Long BookId) {
		List<BookAdminResponse> newBookList = bookAdminService.deleteBook(BookId);
		return ResponseEntity.ok(newBookList);
	}

}
