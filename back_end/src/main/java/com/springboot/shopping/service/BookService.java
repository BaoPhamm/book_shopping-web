package com.springboot.shopping.service;

import java.util.List;
import java.util.Optional;

import com.springboot.shopping.model.Book;

public interface BookService {

	Optional<Book> findBookById(Long bookId);

	List<Book> findAllBooks();

	Book createBook(Book book);

	Book updateBook(Long bookId, Book book);

	List<Book> deleteBook(Long bookId);

}
