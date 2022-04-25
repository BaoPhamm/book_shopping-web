package com.springboot.shopping.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.springboot.shopping.model.Book;
import com.springboot.shopping.repository.BookRepository;
import com.springboot.shopping.repository.CategoryRepository;
import com.springboot.shopping.service.impl.BookServiceImpl;

class BookServiceTest {

	private BookServiceImpl bookServiceImpl;
	private BookRepository bookRepository;
	private CategoryRepository categoryRepository;
	private Book expectedBook;
	private Book expectedBookSecond;
	private List<Book> expectedBookList;

	@BeforeEach
	void beforeEach() {
		// Mock BookRepository 
		bookRepository = mock(BookRepository.class);
		bookServiceImpl = new BookServiceImpl(bookRepository, categoryRepository);
		
		// Book 1
		expectedBook = Book.builder()
				.id(123L)
				.title("ABCXYZ")
				.author("baobao")
				.totalPages(100)
				.requiredAge(18)
				.releaseDate(LocalDate.now())
				.price(100000)
				.imgSrc("https://www.google.com")
				.description("nothing")
				.build();
		// Book 2
		expectedBookSecond = Book.builder()
				.id(124L)
				.title("QWERTY")
				.author("baopham")
				.totalPages(200)
				.requiredAge(13)
				.releaseDate(LocalDate.now())
				.price(200000)
				.imgSrc("https://www.google.com")
				.description("nothing too")
				.build();
		
		// Add 2 books to expected BookList
		expectedBookList = new ArrayList<>();
		expectedBookList.add(expectedBook);
		expectedBookList.add(expectedBookSecond);
		
		// Return a book with valid Id
		when(bookRepository.findById(123L)).thenReturn(Optional.of(expectedBook));
		// Throw an Exception with invalid Id
		when(bookRepository.findById(125L)).thenThrow(new NoSuchElementException("No value present"));
		// Return all books in expected BookList
		when(bookRepository.findAll()).thenReturn(expectedBookList);
		// When save successfully, return this book
		when(bookRepository.save(any())).thenReturn(expectedBook);
	}

	// UnitTest for function findBookById()
	@Test
	void findBookById_ShouldReturnBook_WhenIdValid() {
		Optional<Book> bookResult = bookServiceImpl.findBookById(123L);
		assertThat(bookResult.get().equals(expectedBook), is(true));
		
		// Expect functions call
		verify(bookRepository, times(1)).findById(123L);
	}
	@Test
	void findBookById_ShouldThrowException_WhenIdInValid() {
		Exception exception = assertThrows(NoSuchElementException.class, () ->
		bookServiceImpl.findBookById(125L));
		assertThat(exception.getMessage(), is("No value present"));
		
		// Expect functions call
		verify(bookRepository, times(1)).findById(125L);
	}

	// UnitTest for function findAllBooks()
	@Test
	void findAllBooks_ShouldReturnAllBooks() {
		List<Book> bookListResult = bookServiceImpl.findAllBooks();
		bookListResult.forEach(book -> {
			assertThat(book.equals(expectedBookList.get(bookListResult.indexOf(book))), is(true));
		});
		assertThat(bookListResult.size(), is(2));
		
		// Expect functions call
		verify(bookRepository, times(1)).findAll();
	}

	// UnitTest for function createBook()
	@Test
	void createBook_ShouldCreateBook() {
		Book bookResult = bookServiceImpl.createBook(expectedBook);
		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book createdBook = bookCaptor.getValue();
		assertThat(bookResult.equals(createdBook), is(true));
		
		// Expect functions call
		verify(bookRepository, times(1)).save(createdBook);
	}

	// UnitTest for function updateBook()
	@Test
	void updateBook_ShouldReturnUpdatedBook_WhenIdValid() {
		Book bookResult = bookServiceImpl.updateBook(123L, expectedBook);
		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book createdBook = bookCaptor.getValue();
		assertThat(bookResult.equals(createdBook), is(true));
		
		// Expect functions call
		verify(bookRepository, times(1)).findById(123L);
		verify(bookRepository, times(1)).save(createdBook);
	}
	@Test
	void updateBook_ShouldThrowException_WhenIdInValid() {
		Exception exception = assertThrows(NoSuchElementException.class, () ->
		bookServiceImpl.updateBook(125L, expectedBook));
		assertThat(exception.getMessage(), is("No value present"));
		
		// Expect functions call
		verify(bookRepository, times(1)).findById(125L);
	}

	// UnitTest for function deleteBook()
	@Test
	void deleteBook_ShouldReturnUpdatedBookList_WhenIdValid() {
		List<Book> bookListResult = bookServiceImpl.deleteBook(123L);
		bookListResult.forEach(book -> {
			assertThat(book.equals(expectedBookList.get(bookListResult.indexOf(book))), is(true));
		});
		assertThat(bookListResult.size(), is(2));
		
		// Expect functions call
		verify(bookRepository, times(1)).findById(123L);
		verify(bookRepository, times(1)).deleteById(123L);
		verify(bookRepository, times(1)).findAll();
	}
	@Test
	void deleteBook_ShouldThrowException_WhenIdInValid() {
		Exception exception = assertThrows(NoSuchElementException.class, () ->
		bookServiceImpl.deleteBook(125L));
		assertThat(exception.getMessage(), is("No value present"));
		
		// Expect functions call
		verify(bookRepository, times(1)).findById(125L);
	}

}
