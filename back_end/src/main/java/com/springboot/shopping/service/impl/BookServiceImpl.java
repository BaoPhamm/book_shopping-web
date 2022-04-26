package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.exception.book.BookExistException;
import com.springboot.shopping.exception.book.BookNotFoundException;
import com.springboot.shopping.exception.category.CategoryNotFoundException;
import com.springboot.shopping.exception.user.UserRoleExistException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Book;
import com.springboot.shopping.model.Category;
import com.springboot.shopping.repository.BookRepository;
import com.springboot.shopping.repository.CategoryRepository;
import com.springboot.shopping.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	private final CommonMapper commonMapper;

	@Override
	public List<BookResponse> findAllBooks() {
		return commonMapper.convertToResponseList(bookRepository.findAll(), BookResponse.class);
	}

	@Override
	public BookResponse findBookById(Long bookId) {
		return commonMapper.convertToResponse(bookRepository.findById(bookId), BookResponse.class);
	}

	@Override
	public BookResponse createBook(BookRequest bookRequest) {

		Book newBook = commonMapper.convertToEntity(bookRequest, Book.class);
		Optional<Book> bookFromDb = bookRepository.findByTitle(bookRequest.getTitle());
		if (bookFromDb.isPresent()) {
			throw new BookExistException();
		}
		return commonMapper.convertToResponse(bookRepository.save(newBook), BookResponse.class);
	}

	@Override
	public String addCategoryToBook(String bookTitle, String categoryName) {

		Optional<Book> bookFromDb = bookRepository.findByTitle(bookTitle);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException();
		}
		Optional<Category> categoryFromDb = categoryRepository.findByName(categoryName);
		if (categoryFromDb.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		if (bookFromDb.get().getCategories().contains(categoryFromDb.get())) {
			throw new UserRoleExistException();
		}
		bookFromDb.get().getCategories().add(categoryFromDb.get());
		bookRepository.save(bookFromDb.get());
		return "Category successfully added.";
	}

	@Override
	public BookResponse updateBook(Long bookId, BookRequest bookRequest) {

		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException();
		}
		bookFromDb.get().setId(bookId);
		return commonMapper.convertToResponse(bookRepository.save(bookFromDb.get()), BookResponse.class);
	}

	@Override
	@Transactional
	public List<BookResponse> deleteBook(Long bookId) {

		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException();
		}
		bookRepository.deleteById(bookId);
		return commonMapper.convertToResponseList(bookRepository.findAll(), BookResponse.class);
	}

	@Override
	public List<BookResponse> findBooksByCategory(String categoryName) {

		Optional<Category> categoryFromDb = categoryRepository.findByName(categoryName);
		if (categoryFromDb.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		return commonMapper.convertToResponseList(bookRepository.findByCategory(categoryName), BookResponse.class);

	}

}
