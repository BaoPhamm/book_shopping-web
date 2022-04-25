package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.shopping.exception.ApiRequestException;
import com.springboot.shopping.exception.UserRoleExistException;
import com.springboot.shopping.model.Book;
import com.springboot.shopping.model.Category;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.repository.BookRepository;
import com.springboot.shopping.repository.CategoryRepository;
import com.springboot.shopping.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> findBookById(Long bookId) {
		return bookRepository.findById(bookId);
	}

	@Override
	public Book createBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public String addCategoryToBook(String bookTitle, String categoryName) {
		Book book = bookRepository.findByTitle(bookTitle)
				.orElseThrow(() -> new ApiRequestException("Book not found!", HttpStatus.NOT_FOUND));
		Category category = categoryRepository.findByName(categoryName)
				.orElseThrow(() -> new ApiRequestException("Category not found!", HttpStatus.NOT_FOUND));
		if (book.getCategories().contains(category)) {
			throw new UserRoleExistException("Book already has this category !");
		}
		book.getCategories().add(category);
		bookRepository.save(book);
		return "Category successfully added.";
	}

	@Override
	public Book updateBook(Long bookId, Book book) {
		bookRepository.findById(bookId)
				.orElseThrow(() -> new ApiRequestException("Book not found!", HttpStatus.NOT_FOUND));
		book.setId(bookId);
		return bookRepository.save(book);
	}

	@Override
	@Transactional
	public List<Book> deleteBook(Long bookId) {
		bookRepository.findById(bookId)
				.orElseThrow(() -> new ApiRequestException("Book not found!", HttpStatus.NOT_FOUND));
		bookRepository.deleteById(bookId);
		return bookRepository.findAll();
	}

	@Override
	public List<Book> findBooksByCategory(String categoryName) {
		categoryRepository.findByName(categoryName)
				.orElseThrow(() -> new ApiRequestException("Category not found!", HttpStatus.NOT_FOUND));
		return bookRepository.findByCategory(categoryName);
	}

}
