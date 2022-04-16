package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.shopping.exception.ApiRequestException;
import com.springboot.shopping.model.Book;
import com.springboot.shopping.repository.BookRepository;
import com.springboot.shopping.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	@Override
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> findBookById(Long bookId) {
		return bookRepository.findById(bookId); // TODO add test
	}

	@Override
	public Book createBook(@RequestBody Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Book updateBook(Long bookId, Book bookRequest) {
		bookRepository.findById(bookId)
				.orElseThrow(() -> new ApiRequestException("Book not found!", HttpStatus.NOT_FOUND));
		bookRequest.setId(bookId);
		return bookRepository.save(bookRequest);
	}

	@Override
	@Transactional
	public List<Book> deleteBook(Long bookId) {
		bookRepository.findById(bookId)
				.orElseThrow(() -> new ApiRequestException("Book not found!", HttpStatus.NOT_FOUND));
		bookRepository.deleteById(bookId);
		return bookRepository.findAll();
	}

}
