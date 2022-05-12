package com.springboot.shopping.service.book;

import java.util.List;

import com.springboot.shopping.dto.book.BookResponse;

public interface BookService {

	BookResponse findBookById(Long bookId);

	List<BookResponse> findAllBooks();

	List<BookResponse> findFeaturesBooks();

	List<BookResponse> findBooksByCategory(Long categoryId);
}
