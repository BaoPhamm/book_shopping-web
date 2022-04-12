package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.shopping.model.Book;
import com.springboot.shopping.repository.BookRepository;
import com.springboot.shopping.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	@Override
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> findBookById(Long bookId) {
		return bookRepository.findById(bookId); // TODO add test
	}

}
