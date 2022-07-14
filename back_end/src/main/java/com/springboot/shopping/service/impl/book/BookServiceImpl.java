package com.springboot.shopping.service.impl.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.exception.book.BookNotFoundException;
import com.springboot.shopping.exception.category.CategoryNotFoundException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Book;
import com.springboot.shopping.repository.BookRepository;
import com.springboot.shopping.repository.CategoryRepository;
import com.springboot.shopping.service.book.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	private final CommonMapper commonMapper;

	@Override
	public List<BookResponse> findAllBooks(Pageable pageable) {
		Page<Book> page = bookRepository.findAll(pageable);
		List<Book> bookList = page.getContent();
		return commonMapper.convertToResponseList(bookList, BookResponse.class);
	}

	@Override
	public BookResponse findBookById(Long bookId) {
		Book bookFromDb = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException());
		return commonMapper.convertToResponse(bookFromDb, BookResponse.class);
	}

	@Override
	public List<BookResponse> findBooksByCategory(Long categoryId) {
		categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException());

		List<Book> bookList = bookRepository.findByCategory(categoryId);
		return commonMapper.convertToResponseList(bookList, BookResponse.class);
	}

	@Override
	public List<BookResponse> findFeaturesBooks() {
		return commonMapper.convertToResponseList(bookRepository.findFeaturesBooks(), BookResponse.class);
	}

	@Override
	public Long getTotalBooks() {
		long totalBooks = bookRepository.count();
		return totalBooks;
	}

	@Override
	public Long getTotalBooksByCategory(Long categoryId) {
		long totalBooksByCategory = bookRepository.getTotalBooksByCategory(categoryId);
		return totalBooksByCategory;
	}

}
