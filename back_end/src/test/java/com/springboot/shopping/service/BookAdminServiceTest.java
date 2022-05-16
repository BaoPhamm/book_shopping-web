package com.springboot.shopping.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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
import com.springboot.shopping.service.book.impl.BookAdminServiceImpl;

class BookAdminServiceTest {

	private BookAdminServiceImpl bookAdminServiceImpl;
	private BookRepository bookRepository;
	private CategoryRepository categoryRepository;
	private CommonMapper commonMapper;
	private BookRequest bookCreateRequestInitial;
	private BookRequest bookUpdateRequestInitial;
	private BookAdminResponse expectedFirstBookResponse;
	private List<BookAdminResponse> expectedListBookResponse;

	private Book expectedFirstBook;
	private Book bookInitial;
	private Book bookUpdateRequestConvertToEntity;
	private List<Book> expectedBookList;
	private List<Category> categoryListInitial;
	private Set<Category> bookCategories;
	private Category categoryInitial;
	private Category categoryInitialFirst;
	private Category categoryInitialSecond;
	private Category categoryInitialThird;
	private Category categoryInitialFourth;
	private Category categoryInitialFifth;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void beforeEach() {
		// Mock BookRepository
		bookRepository = mock(BookRepository.class);
		// Mock CategoryRepository
		categoryRepository = mock(CategoryRepository.class);
		// Mock CommonMapper
		commonMapper = mock(CommonMapper.class);

		bookAdminServiceImpl = new BookAdminServiceImpl(bookRepository, categoryRepository, commonMapper);

		expectedFirstBook = mock(Book.class);
		expectedBookList = mock(List.class);

		expectedFirstBookResponse = mock(BookAdminResponse.class);
		expectedListBookResponse = mock(List.class);

		bookCreateRequestInitial = mock(BookRequest.class);
		bookUpdateRequestInitial = mock(BookRequest.class);
		bookUpdateRequestConvertToEntity = mock(Book.class);

		categoryInitial = mock(Category.class);
		categoryListInitial = new ArrayList<>();
		categoryInitialFirst = Category.builder().id(1L).name("category1").build();
		categoryInitialSecond = Category.builder().id(2L).name("category2").build();
		categoryInitialThird = Category.builder().id(3L).name("category3").build();
		categoryInitialFourth = Category.builder().id(4L).name("category4").build();
		categoryInitialFifth = Category.builder().id(5L).name("category5").build();
		categoryListInitial.add(categoryInitialFirst);
		categoryListInitial.add(categoryInitialSecond);
		categoryListInitial.add(categoryInitialThird);
		categoryListInitial.add(categoryInitialFourth);

		expectedFirstBook.setCategories(new HashSet<>(List.of(categoryInitialFirst, categoryInitialSecond)));

		bookInitial = Book.builder().id(1L)
				.categories(new HashSet<>(List.of(categoryInitialFirst, categoryInitialSecond))).build();
	}

	// UnitTest for function findBookById()
	@Test
	void findBookById_ShouldReturnBook_WhenIdValid() {

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(commonMapper.convertToResponse(expectedFirstBook, BookAdminResponse.class))
				.thenReturn(expectedFirstBookResponse);

		BookAdminResponse bookResponseResult = bookAdminServiceImpl.findBookById(expectedFirstBook.getId());

		assertThat(bookResponseResult, is(expectedFirstBookResponse));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
		verify(commonMapper, times(1)).convertToResponse(expectedFirstBook, BookAdminResponse.class);
	}

	@Test
	void findBookById_ShouldThrowException_WhenIdInValid() {

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.empty());

		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookAdminServiceImpl.findBookById(expectedFirstBook.getId()));

		assertThat(exception.getMessage(), is("Book is not found!"));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
	}

	// UnitTest for function findAllBooks()
	@Test
	void findAllBooks_ShouldReturnAllBooks() {

		when(bookRepository.findAll()).thenReturn(expectedBookList);
		when(commonMapper.convertToResponseList(expectedBookList, BookAdminResponse.class))
				.thenReturn(expectedListBookResponse);

		List<BookAdminResponse> bookResponseListResult = bookAdminServiceImpl.findAllBooks();

		assertThat(bookResponseListResult, is(expectedListBookResponse));

		verify(bookRepository, times(1)).findAll();
		verify(commonMapper, times(1)).convertToResponseList(expectedBookList, BookAdminResponse.class);
	}

	// UnitTest for function createBook()
	@Test
	void createBook_ShouldCreateBook_WhenTitleValid() {

		BookRequest bookRequestInitial = mock(BookRequest.class);

		BookAdminResponse expectedBookResponse = mock(BookAdminResponse.class);

		when(bookRepository.findByTitle(bookRequestInitial.getTitle())).thenReturn(Optional.empty());
		when(commonMapper.convertToEntity(bookRequestInitial, Book.class)).thenReturn(expectedFirstBook);
		when(bookRepository.save(expectedFirstBook)).thenReturn(expectedFirstBook);
		when(commonMapper.convertToResponse(expectedFirstBook, BookAdminResponse.class))
				.thenReturn(expectedBookResponse);

		BookAdminResponse bookResponseResult = bookAdminServiceImpl.createBook(bookRequestInitial);

		assertThat(bookResponseResult, is(expectedBookResponse));

		verify(bookRepository, times(1)).findByTitle(bookRequestInitial.getTitle());
		verify(commonMapper, times(1)).convertToEntity(bookRequestInitial, Book.class);
		verify(bookRepository, times(1)).save(expectedFirstBook);
		verify(commonMapper, times(1)).convertToResponse(expectedFirstBook, BookAdminResponse.class);
	}

	@Test
	void createBook_ShouldThrowBookExistException_WhenTitleInValid() {

		when(bookRepository.findByTitle(bookCreateRequestInitial.getTitle()))
				.thenReturn(Optional.of(expectedFirstBook));

		Exception exception = assertThrows(BookExistException.class,
				() -> bookAdminServiceImpl.createBook(bookCreateRequestInitial));

		assertThat(exception.getMessage(),
				is("Title \"" + bookCreateRequestInitial.getTitle() + "\" is already existed!"));

		verify(bookRepository, times(1)).findByTitle(bookCreateRequestInitial.getTitle());
	}

	// UnitTest for function addCategoriesToBook()
	@Test
	void addCategoriesToBook_ShouldAddCategoriesToBook() {

		List<Long> newCategoriesIdToAdd = List.of(3L, 4L);

		when(bookRepository.findById(bookInitial.getId())).thenReturn(Optional.of(bookInitial));
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		String stringResult = bookAdminServiceImpl.addCategoriesToBook(bookInitial.getId(), newCategoriesIdToAdd);

		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book savedBook = bookCaptor.getValue();

		List<Category> savedCategoriesList = new ArrayList<Category>(savedBook.getCategories());
		Collections.sort(savedCategoriesList, Comparator.comparingLong(Category::getId));

		assertThat(savedCategoriesList, is(categoryListInitial));
		assertThat(stringResult, is("Category successfully added."));

		verify(bookRepository, times(1)).findById(bookInitial.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void addCategoriesToBook_ShouldThrowCategoryExistException_WhenCategoryAlreadyExistInBook() {

		List<Long> newCategoriesIdToAdd = List.of(2L, 4L);

		when(bookRepository.findById(bookInitial.getId())).thenReturn(Optional.of(bookInitial));
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		Exception exception = assertThrows(CategoryExistException.class,
				() -> bookAdminServiceImpl.addCategoriesToBook(bookInitial.getId(), newCategoriesIdToAdd));
		assertThat(exception.getMessage(), is("Category with id: 2 is already existed in this book!"));

		verify(bookRepository, times(1)).findById(bookInitial.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void addCategoriesToBook_ShouldThrowCategoryNotFoundException_WhenCategoryNotFound() {

		List<Long> newCategoriesIdToAdd = List.of(3L, 5L);

		when(bookRepository.findById(bookInitial.getId())).thenReturn(Optional.of(bookInitial));
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		Exception exception = assertThrows(CategoryNotFoundException.class,
				() -> bookAdminServiceImpl.addCategoriesToBook(bookInitial.getId(), newCategoriesIdToAdd));
		assertThat(exception.getMessage(), is("Category with id: 5 is not found!"));

		verify(bookRepository, times(1)).findById(bookInitial.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void addCategoriesToBook_ShouldThrowBookNotFoundException_WhenBookNotFound() {

		List<Long> newCategoriesIdToAdd = List.of(3L, 5L);

		when(bookRepository.findById(bookInitial.getId())).thenReturn(Optional.empty());

		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookAdminServiceImpl.addCategoriesToBook(bookInitial.getId(), newCategoriesIdToAdd));
		assertThat(exception.getMessage(), is("Book with id: " + bookInitial.getId() + " is not found!"));

		verify(bookRepository, times(1)).findById(bookInitial.getId());
	}

	// UnitTest for function removeCategoriesFromBook()
	@Test
	void removeCategoriesFromBook_ShouldRemoveCategoriesFromBook() {

		List<Long> categoriesIdToRemove = List.of(1L, 2L);
		List<Category> savedCategoriesListExpected = List.of(categoryInitialThird);

		Book bookInitial = Book.builder().id(1L)
				.categories(new HashSet<>(List.of(categoryInitialFirst, categoryInitialSecond, categoryInitialThird)))
				.build();

		when(bookRepository.findById(bookInitial.getId())).thenReturn(Optional.of(bookInitial));
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		String stringResult = bookAdminServiceImpl.removeCategoriesFromBook(bookInitial.getId(), categoriesIdToRemove);

		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book savedBook = bookCaptor.getValue();

		List<Category> savedCategoriesList = new ArrayList<Category>(savedBook.getCategories());
		Collections.sort(savedCategoriesList, Comparator.comparingLong(Category::getId));

		assertThat(savedCategoriesList, is(savedCategoriesListExpected));
		assertThat(stringResult, is("Category successfully removed."));

		verify(bookRepository, times(1)).findById(bookInitial.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void removeCategoriesFromBook_ShouldThrowCategoryNotFoundInBookException_WhenCategoryNotFoundInBook() {

		List<Long> categoriesIdToRemove = List.of(1L, 3L);

		when(bookRepository.findById(bookInitial.getId())).thenReturn(Optional.of(bookInitial));
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		Exception exception = assertThrows(CategoryNotFoundInBookException.class,
				() -> bookAdminServiceImpl.removeCategoriesFromBook(bookInitial.getId(), categoriesIdToRemove));
		assertThat(exception.getMessage(), is("Category 3 is not found in this book!"));

		verify(bookRepository, times(1)).findById(bookInitial.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void removeCategoriesFromBook_ShouldThrowCategoryNotFoundException_WhenCategoryNotFound() {

		List<Long> categoriesIdToRemove = List.of(1L, 5L);
		Book bookInitial = Book.builder().id(1L)
				.categories(new HashSet<>(List.of(categoryInitialFirst, categoryInitialSecond, categoryInitialFifth)))
				.build();

		when(bookRepository.findById(bookInitial.getId())).thenReturn(Optional.of(bookInitial));
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		Exception exception = assertThrows(CategoryNotFoundException.class,
				() -> bookAdminServiceImpl.removeCategoriesFromBook(bookInitial.getId(), categoriesIdToRemove));
		assertThat(exception.getMessage(), is("Category with id: 5 is not found!"));

		verify(bookRepository, times(1)).findById(bookInitial.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void removeCategoriesFromBook_ShouldThrowBookNotFoundException_WhenBookNotFound() {

		List<Long> categoriesIdToRemove = List.of(1L, 5L);

		when(bookRepository.findById(bookInitial.getId())).thenReturn(Optional.empty());

		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookAdminServiceImpl.removeCategoriesFromBook(bookInitial.getId(), categoriesIdToRemove));
		assertThat(exception.getMessage(), is("Book with id: " + bookInitial.getId() + " is not found!"));

		verify(bookRepository, times(1)).findById(bookInitial.getId());
	}

	// UnitTest for function updateBook()
	@Test
	void updateBook_ShouldReturnUpdatedBook_WhenIdAndTitleValid() {

		bookUpdateRequestInitial = mock(BookRequest.class);

		when(bookRepository.findById(bookUpdateRequestInitial.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findByTitle(bookUpdateRequestInitial.getTitle())).thenReturn(Optional.empty());
		when(bookUpdateRequestInitial.getTitle()).thenReturn("title");
		when(commonMapper.convertToEntity(bookUpdateRequestInitial, Book.class))
				.thenReturn(bookUpdateRequestConvertToEntity);
		when(bookRepository.save(any())).thenReturn(bookUpdateRequestConvertToEntity);
		when(commonMapper.convertToResponse(bookUpdateRequestConvertToEntity, BookAdminResponse.class))
				.thenReturn(expectedFirstBookResponse);

		BookAdminResponse BookResponseResult = bookAdminServiceImpl.updateBook(bookUpdateRequestInitial);

		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book savedBook = bookCaptor.getValue();

		assertThat(savedBook, is(bookUpdateRequestConvertToEntity));
		assertThat(BookResponseResult, is(expectedFirstBookResponse));

		verify(bookRepository, times(1)).findById(bookUpdateRequestInitial.getId());
		verify(bookRepository, times(1)).findByTitle(bookUpdateRequestInitial.getTitle());
		verify(commonMapper, times(1)).convertToEntity(bookUpdateRequestInitial, Book.class);
		verify(bookRepository, times(1)).save(any());
		verify(commonMapper, times(1)).convertToResponse(bookUpdateRequestConvertToEntity, BookAdminResponse.class);
	}

	@Test
	void updateBook_ShouldThrowBookExistException_WhenTitleAlreadyInUse() {

		when(bookRepository.findById(bookUpdateRequestInitial.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookUpdateRequestInitial.getTitle()).thenReturn("title");
		when(bookRepository.findByTitle(bookUpdateRequestInitial.getTitle()))
				.thenReturn(Optional.of(expectedFirstBook));

		Exception exception = assertThrows(BookExistException.class,
				() -> bookAdminServiceImpl.updateBook(bookUpdateRequestInitial));
		assertThat(exception.getMessage(),
				is("Title \"" + bookUpdateRequestInitial.getTitle() + "\" is already existed!"));

		verify(bookRepository, times(1)).findById(bookUpdateRequestInitial.getId());
		verify(bookRepository, times(1)).findByTitle(bookUpdateRequestInitial.getTitle());
	}

	@Test
	void updateBook_ShouldThrowBookNotFoundException_WhenBookNotFound() {

		when(bookRepository.findById(bookUpdateRequestInitial.getId())).thenReturn(Optional.empty());
		when(bookUpdateRequestInitial.getId()).thenReturn(1L);

		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookAdminServiceImpl.updateBook(bookUpdateRequestInitial));
		assertThat(exception.getMessage(), is("Book with id: " + bookUpdateRequestInitial.getId() + " is not found!"));

		verify(bookRepository, times(1)).findById(bookUpdateRequestInitial.getId());
	}

	// UnitTest for function deleteBook()
	@Test
	void deleteBook_ShouldReturnSuccessMessage_WhenIdValid() {

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));

		String messageResult = bookAdminServiceImpl.deleteBook(expectedFirstBook.getId());

		assertThat(messageResult, is("Book successfully deleted."));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
		verify(bookRepository, times(1)).deleteById(expectedFirstBook.getId());
	}

	@Test
	void deleteBook_ShouldBookNotFoundException_WhenBookNotFound() {

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.empty());

		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookAdminServiceImpl.deleteBook(expectedFirstBook.getId()));
		assertThat(exception.getMessage(), is("Book with id: " + expectedFirstBook.getId() + " is not found!"));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
	}

	// UnitTest for function findBooksByCategory()
	@Test
	void findBooksByCategory_ShouldReturndBookResponseList_WhenCategoryNameValid() {

		when(categoryRepository.findById(categoryInitialFirst.getId())).thenReturn(Optional.of(categoryInitialFirst));
		when(bookRepository.findByCategory(categoryInitialFirst.getId())).thenReturn(expectedBookList);
		when(commonMapper.convertToResponseList(expectedBookList, BookAdminResponse.class))
				.thenReturn(expectedListBookResponse);

		List<BookAdminResponse> bookResponseListResult = bookAdminServiceImpl
				.findBooksByCategory(categoryInitialFirst.getId());

		assertThat(bookResponseListResult, is(expectedListBookResponse));

		verify(categoryRepository, times(1)).findById(categoryInitialFirst.getId());
		verify(bookRepository, times(1)).findByCategory(categoryInitialFirst.getId());
		verify(commonMapper, times(1)).convertToResponseList(expectedBookList, BookAdminResponse.class);
	}

	// UnitTest for function findBooksByCategory()
	@Test
	void findBooksByCategory_ShouldThrowCategoryNotFoundException_WhenCategoryNotFound() {

		when(categoryRepository.findById(categoryInitial.getId())).thenReturn(Optional.empty());
		when(categoryInitial.getId()).thenReturn(1L);

		Exception exception = assertThrows(CategoryNotFoundException.class,
				() -> bookAdminServiceImpl.findBooksByCategory(categoryInitial.getId()));
		assertThat(exception.getMessage(), is("Category with id: " + categoryInitial.getId() + " is not found!"));

		verify(categoryRepository, times(1)).findById(categoryInitial.getId());
	}

	// UnitTest for function findBooksByCategory()
	@Test
	void findFeaturesBooks_ShouldReturndBookResponseList() {

		when(bookRepository.findFeaturesBooks()).thenReturn(expectedBookList);
		when(commonMapper.convertToResponseList(expectedBookList, BookAdminResponse.class))
				.thenReturn(expectedListBookResponse);

		List<BookAdminResponse> bookResponseListResult = bookAdminServiceImpl.findFeaturesBooks();

		assertThat(bookResponseListResult, is(expectedListBookResponse));

		verify(bookRepository, times(1)).findFeaturesBooks();
		verify(commonMapper, times(1)).convertToResponseList(expectedBookList, BookAdminResponse.class);
	}

}
