package com.springboot.shopping.service;

import java.util.List;
import java.util.Optional;

import com.springboot.shopping.model.Book;

public interface BookService {

	Optional<Book> findBookById(Long bookId);

	List<Book> findAllBooks();

}
