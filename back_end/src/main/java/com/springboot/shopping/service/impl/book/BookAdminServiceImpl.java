package com.springboot.shopping.service.impl.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public List<BookAdminResponse> findAllBooks(Pageable pageable) {
		Page<Book> page = bookRepository.findAll(pageable);
		List<Book> bookList = page.getContent();
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

	private void findCategoryToThrowExcepion(List<Category> allCategories, Long categoryId) {
		for (Category category : allCategories) {
			if (category.getId() != categoryId) {
				throw new CategoryNotFoundException(categoryId);
			}
		}
	}

	@Override
	public String addCategoriesToBook(Long bookId, List<Long> categoryIds) {

		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException(bookId);
		}
		List<Long> bookCategoryIds = new ArrayList<>();
		bookFromDb.get().getCategories().stream().forEach((Category) -> {
			bookCategoryIds.add(Category.getId());
		});
		List<Category> allValidCategoryIds = categoryRepository.findAllById(categoryIds);
		System.out.println(allValidCategoryIds.size());
		System.out.println(categoryIds.size());

		if (allValidCategoryIds.size() == 0) {
			throw new CategoryNotFoundException(categoryIds.get(0));
		} else if (allValidCategoryIds.size() < categoryIds.size()) {
			categoryIds.stream().forEach(categorieId -> {
				findCategoryToThrowExcepion(allValidCategoryIds, categorieId);
			});
		} else if (allValidCategoryIds.size() == categoryIds.size()) {
			categoryIds.forEach(categoryId -> {
				if (bookCategoryIds.contains(categoryId)) {
					throw new CategoryExistException(categoryId);
				}
			});
		}

		bookFromDb.get().getCategories().addAll(allValidCategoryIds);
		bookRepository.save(bookFromDb.get());
		return "Category successfully added.";
	}

	@Override
	public String removeCategoriesFromBook(Long bookId, List<Long> categoryIds) {

		Optional<Book> bookFromDb = bookRepository.findById(bookId);
		if (bookFromDb.isEmpty()) {
			throw new BookNotFoundException(bookId);
		}
		List<Long> bookCategoryIds = new ArrayList<>();
		bookFromDb.get().getCategories().stream().forEach((Category) -> {
			bookCategoryIds.add(Category.getId());
		});

		List<Category> allValidCategoryIds = categoryRepository.findAllById(categoryIds);

		if (allValidCategoryIds.size() == 0) {
			throw new CategoryNotFoundException(categoryIds.get(0));
		} else if (allValidCategoryIds.size() < categoryIds.size()) {
			categoryIds.stream().forEach(categorieId -> {
				findCategoryToThrowExcepion(allValidCategoryIds, categorieId);
			});
		} else if (allValidCategoryIds.size() == categoryIds.size()) {
			categoryIds.forEach(categoryId -> {
				if (!bookCategoryIds.contains(categoryId)) {
					throw new CategoryNotFoundInBookException(categoryId);
				}
			});
		}
		bookFromDb.get().getCategories().removeAll(allValidCategoryIds);
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
