package com.springboot.shopping.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.model.Book;
import com.springboot.shopping.service.BookService;

@Component
public class BookMapper {

	private final CommonMapper commonMapper;
	private final BookService bookService;

	public BookMapper(CommonMapper commonMapper, BookService bookService) {
		super();
		this.commonMapper = commonMapper;
		this.bookService = bookService;
	}

	public BookResponse findBookById(Long bookId) {
		return commonMapper.convertToResponse(bookService.findBookById(bookId).get(), BookResponse.class);
	}

	public List<BookResponse> findAllBooks() {
		return commonMapper.convertToResponseList(bookService.findAllBooks(), BookResponse.class);
	}

	public BookResponse createBook(BookRequest bookRequest) {
		Book book = commonMapper.convertToEntity(bookRequest, Book.class);
		return commonMapper.convertToResponse(bookService.createBook(book), BookResponse.class);
	}

	public BookResponse updateBook(Long bookId, BookRequest bookRequest) {
		Book book = commonMapper.convertToEntity(bookRequest, Book.class);
		return commonMapper.convertToResponse(bookService.updateBook(bookId, book), BookResponse.class);
	}

	public List<BookResponse> deleteBook(Long bookId) {
		return commonMapper.convertToResponseList(bookService.deleteBook(bookId), BookResponse.class);
	}

}
