package com.springboot.shopping.service;

import java.util.List;

import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookResponse;

public interface BookService {

	BookResponse findBookById(Long bookId);

	List<BookResponse> findAllBooks();
	
	List<BookResponse> findBooksByCategory(Long categoryId);
	
	String addCategoriesToBook(Long bookId, List<Long> categoriesId);
	
	String removeCategoriesFromBook(Long bookId, List<Long> categoriesId);

	BookResponse createBook(BookRequest bookRequest);

	BookResponse updateBook(BookRequest bookRequest);

	List<BookResponse> deleteBook(Long bookId);

}
