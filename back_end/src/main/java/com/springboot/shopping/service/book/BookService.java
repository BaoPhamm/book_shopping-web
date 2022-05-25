package com.springboot.shopping.service.book;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.springboot.shopping.dto.book.BookResponse;

public interface BookService {

	BookResponse findBookById(Long bookId);

	List<BookResponse> findAllBooks(Pageable pageable);

	List<BookResponse> findFeaturesBooks();

	List<BookResponse> findBooksByCategory(Long categoryId);

	Long getTotalBooks();

	Long getTotalBooksByCategory(Long categoryId);
}
