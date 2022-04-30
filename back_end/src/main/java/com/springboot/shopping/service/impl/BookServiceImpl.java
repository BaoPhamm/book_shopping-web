package com.springboot.shopping.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.exception.book.BookExistException;
import com.springboot.shopping.exception.book.BookNotFoundException;
import com.springboot.shopping.exception.category.CategoryExistException;
import com.springboot.shopping.exception.category.CategoryNotFoundException;
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
		List<Book> bookList = bookRepository.findAll();
		return commonMapper.convertToResponseList(bookList, BookResponse.class);
	}

	@Override
	public BookResponse findBookById(Long bookId) {
		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException();
		}
		return commonMapper.convertToResponse(bookFromDb.get(), BookResponse.class);
	}

	@Override
	public BookResponse createBook(BookRequest bookRequest) {

		Optional<Book> bookFromDb = bookRepository.findById(bookRequest.getId());
		if (bookFromDb.isPresent()) {
			throw new BookExistException();
		}
		Book newBook = commonMapper.convertToEntity(bookRequest, Book.class);
		Book savedBook = bookRepository.save(newBook);
		return commonMapper.convertToResponse(savedBook, BookResponse.class);
	}

	private Category findCategoryExist(List<Category> allCategories, Long categoryId) {
		for (Category category : allCategories) {
			if (category.getId() == categoryId) {
				return category;
			}
		}
		throw new CategoryNotFoundException(categoryId);
	}

	@Override
	public String addCategoriesToBook(Long bookId, List<Long> categoriesId) {

		Collection<Category> validCategories = new ArrayList<>();
		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException();
		}
		List<Long> bookCategoriesId = bookRepository.findAllIdsOfCategories(bookId);
		List<Category> allCategories = categoryRepository.findAll();

		categoriesId.forEach(categoryId -> {
			if (bookCategoriesId.contains(categoryId)) {
				throw new CategoryExistException(categoryId);
			}
			Category validCategory = findCategoryExist(allCategories, categoryId);
			validCategories.add(validCategory);
		});

		bookFromDb.get().getCategories().addAll(validCategories);
		bookRepository.save(bookFromDb.get());
		return "Category successfully added.";
	}

	@Override
	public String removeCategoriesFromBook(Long bookId, List<Long> categoriesId) {

		Collection<Category> validCategoriesToRemove = new ArrayList<>();
		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException();
		}
		List<Long> bookCategoriesId = bookRepository.findAllIdsOfCategories(bookId);
		List<Category> allCategories = categoryRepository.findAll();

		categoriesId.forEach(categoryId -> {
			if (!bookCategoriesId.contains(categoryId)) {
				throw new CategoryNotFoundException(categoryId);
			}
			Category validCategory = findCategoryExist(allCategories, categoryId);
			validCategoriesToRemove.add(validCategory);
		});

		bookFromDb.get().getCategories().removeAll(validCategoriesToRemove);
		bookRepository.save(bookFromDb.get());
		return "Category successfully removed.";
	}

	@Override
	public BookResponse updateBook(BookRequest bookRequest) {

		Optional<Book> bookFromDb = bookRepository.findById(bookRequest.getId());
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException();
		}
		Book newBookInfo = commonMapper.convertToEntity(bookRequest, Book.class);
		newBookInfo.setCategories(bookFromDb.get().getCategories());
		Book updatedBook = bookRepository.save(newBookInfo);
		return commonMapper.convertToResponse(updatedBook, BookResponse.class);
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
	public List<BookResponse> findBooksByCategory(Long categoryId) {

		Optional<Category> categoryFromDb = categoryRepository.findById(categoryId);
		if (categoryFromDb.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		return commonMapper.convertToResponseList(bookRepository.findByCategory(categoryId), BookResponse.class);
	}

}
