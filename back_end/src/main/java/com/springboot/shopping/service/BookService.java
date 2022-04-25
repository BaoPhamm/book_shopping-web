package com.springboot.shopping.service;

import java.util.List;

import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookResponse;

public interface BookService {

	BookResponse findBookById(Long bookId);

	List<BookResponse> findAllBooks();
	
	List<BookResponse> findBooksByCategory(String categoryName);
	
	String addCategoryToBook(String bookTitle, String categoryName);

	BookResponse createBook(BookRequest bookRequest);

	BookResponse updateBook(Long bookId, BookRequest bookRequest);

	List<BookResponse> deleteBook(Long bookId);

}
