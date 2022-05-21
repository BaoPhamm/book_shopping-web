package com.springboot.shopping.service.book;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.springboot.shopping.dto.book.BookAdminResponse;
import com.springboot.shopping.dto.book.BookRequest;

public interface BookAdminService {

	BookAdminResponse findBookById(Long bookId);

	List<BookAdminResponse> findAllBooks(Pageable pageable);

	List<BookAdminResponse> findFeaturesBooks();

	List<BookAdminResponse> findBooksByCategory(Long categoryId);

	String addCategoriesToBook(Long bookId, List<Long> categoriesId);

	String removeCategoriesFromBook(Long bookId, List<Long> categoriesId);

	BookAdminResponse createBook(BookRequest bookRequest);

	BookAdminResponse updateBook(BookRequest bookRequest);

	String deleteBook(Long bookId);

}
