package com.springboot.shopping.service.book.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.shopping.dto.book.BookAdminResponse;
import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.exception.book.BookExistException;
import com.springboot.shopping.exception.book.BookNotFoundException;
import com.springboot.shopping.exception.category.CategoryExistException;
import com.springboot.shopping.exception.category.CategoryNotFoundException;
import com.springboot.shopping.exception.category.CategoryNotFoundInBookException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Book;
import com.springboot.shopping.model.Category;
import com.springboot.shopping.repository.BookRepository;
import com.springboot.shopping.repository.CategoryRepository;
import com.springboot.shopping.service.book.BookAdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookAdminServiceImpl implements BookAdminService {

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	private final CommonMapper commonMapper;

	@Override
	public List<BookAdminResponse> findAllBooks() {
		List<Book> bookList = bookRepository.findAll();
		return commonMapper.convertToResponseList(bookList, BookAdminResponse.class);
	}

	@Override
	public BookAdminResponse findBookById(Long bookId) {
		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException();
		}
		return commonMapper.convertToResponse(bookFromDb.get(), BookAdminResponse.class);
	}

	@Override
	public BookAdminResponse createBook(BookRequest bookRequest) {

		Optional<Book> bookCheckTitleFromDb = bookRepository.findByTitle(bookRequest.getTitle());
		if (bookCheckTitleFromDb.isPresent()) {
			throw new BookExistException(bookRequest.getTitle());
		}
		Book newBook = commonMapper.convertToEntity(bookRequest, Book.class);
		newBook.setRatingPoint(Double.valueOf(0));
		newBook.setTotalRatings(Long.valueOf(0));
		Book savedBook = bookRepository.save(newBook);
		return commonMapper.convertToResponse(savedBook, BookAdminResponse.class);
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

		Set<Category> validCategories = new HashSet<>();
		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException(bookId);
		}
		List<Long> bookCategoriesId = new ArrayList<>();
		bookFromDb.get().getCategories().stream().forEach((Category) -> {
			bookCategoriesId.add(Category.getId());
		});
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
			throw new BookNotFoundException(bookId);
		}
		List<Long> bookCategoriesId = new ArrayList<>();
		bookFromDb.get().getCategories().stream().forEach((Category) -> {
			bookCategoriesId.add(Category.getId());
		});
		List<Category> allCategories = categoryRepository.findAll();

		categoriesId.forEach(categoryId -> {
			if (!bookCategoriesId.contains(categoryId)) {
				throw new CategoryNotFoundInBookException(categoryId);
			}
			Category validCategory = findCategoryExist(allCategories, categoryId);
			validCategoriesToRemove.add(validCategory);
		});

		bookFromDb.get().getCategories().removeAll(validCategoriesToRemove);
		bookRepository.save(bookFromDb.get());
		return "Category successfully removed.";
	}

	@Override
	public BookAdminResponse updateBook(BookRequest bookRequest) {

		Optional<Book> bookFromDb = bookRepository.findById(bookRequest.getId());
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException(bookRequest.getId());
		}
		Optional<Book> bookCheckTitleFromDb = bookRepository.findByTitle(bookRequest.getTitle());
		if (!bookRequest.getTitle().equals(bookFromDb.get().getTitle()) && bookCheckTitleFromDb.isPresent()) {
			throw new BookExistException(bookRequest.getTitle());
		}

		Book newBookInfo = commonMapper.convertToEntity(bookRequest, Book.class);
		newBookInfo.setCategories(bookFromDb.get().getCategories());
		newBookInfo.setCreateDate(bookFromDb.get().getCreateDate());
		newBookInfo.setRatingPoint(bookFromDb.get().getRatingPoint());
		newBookInfo.setTotalRatings(bookFromDb.get().getTotalRatings());
		Book updatedBook = bookRepository.save(newBookInfo);
		return commonMapper.convertToResponse(updatedBook, BookAdminResponse.class);
	}

	@Override
	@Transactional
	public String deleteBook(Long bookId) {

		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException(bookId);
		}
		bookRepository.deleteById(bookId);
		return "Book successfully deleted.";
	}

	@Override
	public List<BookAdminResponse> findBooksByCategory(Long categoryId) {

		Optional<Category> categoryFromDb = categoryRepository.findById(categoryId);
		if (categoryFromDb.isEmpty()) {
			throw new CategoryNotFoundException(categoryId);
		}
		return commonMapper.convertToResponseList(bookRepository.findByCategory(categoryId), BookAdminResponse.class);
	}

	@Override
	public List<BookAdminResponse> findFeaturesBooks() {
		return commonMapper.convertToResponseList(bookRepository.findFeaturesBooks(), BookAdminResponse.class);
	}
}
