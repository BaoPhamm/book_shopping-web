package com.springboot.shopping.service.book;

import java.util.List;

import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookAdminResponse;

public interface BookAdminService {

	BookAdminResponse findBookById(Long bookId);

	List<BookAdminResponse> findAllBooks();
	
	List<BookAdminResponse> findFeaturesBooks();
	
	List<BookAdminResponse> findBooksByCategory(Long categoryId);
	
	String addCategoriesToBook(Long bookId, List<Long> categoriesId);
	
	String removeCategoriesFromBook(Long bookId, List<Long> categoriesId);

	BookAdminResponse createBook(BookRequest bookRequest);

	BookAdminResponse updateBook(BookRequest bookRequest);

	String deleteBook(Long bookId);
	
	Float getBookRatingById(Long bookId);

}
