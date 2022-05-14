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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
	private BookAdminResponse expectedSecondBookResponse;
	private List<BookAdminResponse> expectedListBookResponse;

	private Book expectedFirstBook;
	private Book expectedSecondBook;
	private Book bookUpdateRequestConvertToEntity;
	private List<Book> expectedBookList;
	private List<Category> categoryListInitial;
	private Category categoryInitialFirst;
	private Category categoryInitialSecond;
	private Category categoryInitialThird;
	private Category categoryInitialFourth;

	@BeforeEach
	void beforeEach() {
		// Mock BookRepository
		bookRepository = mock(BookRepository.class);
		// Mock CategoryRepository
		categoryRepository = mock(CategoryRepository.class);
		// Mock CommonMapper
		commonMapper = mock(CommonMapper.class);

		bookAdminServiceImpl = new BookAdminServiceImpl(bookRepository, categoryRepository, commonMapper);

		// right category list for book
		categoryListInitial = new ArrayList<>();
		categoryInitialFirst = Category.builder().id(1L).name("categoryName1").description("description1")
				.imgSrc("imgSrc1").build();
		categoryInitialSecond = Category.builder().id(2L).name("categoryName2").description("description2")
				.imgSrc("imgSrc2").build();
		categoryInitialThird = Category.builder().id(3L).name("categoryName3").description("description3")
				.imgSrc("imgSrc3").build();
		categoryInitialFourth = Category.builder().id(4L).name("categoryName4").description("description4")
				.imgSrc("imgSrc4").build();
		categoryListInitial.add(categoryInitialFirst);
		categoryListInitial.add(categoryInitialSecond);
		categoryListInitial.add(categoryInitialThird);
		categoryListInitial.add(categoryInitialFourth);

		// expected first Book
		expectedFirstBook = Book.builder().id(1L).title("title1").author("author1").totalPages(2L).requiredAge(3L)
				.releaseDate(LocalDate.now()).price(100000).imgSrc("imgSrc1").description("description1")
				.ratingPoint(4.0).totalRatings(5L).createDate(new Date(100000L)).updateDate(new Date(200000L))
				.categories((new HashSet<Category>(List.of(categoryInitialFirst, categoryInitialSecond)))).build();

		// expected second Book
		expectedSecondBook = Book.builder().id(6L).title("title2").author("author2").totalPages(7L).requiredAge(8L)
				.releaseDate(LocalDate.now()).price(200000).imgSrc("imgSrc2").description("description2")
				.ratingPoint(9.0).totalRatings(10L).createDate(new Date(300000L)).updateDate(new Date(400000L))
				.categories((new HashSet<Category>(List.of(categoryInitialThird, categoryInitialFourth)))).build();

		// Add books to BookList
		expectedBookList = new ArrayList<>();
		expectedBookList.add(expectedFirstBook);
		expectedBookList.add(expectedSecondBook);

		// Init bookRequest Input
		bookCreateRequestInitial = BookRequest.builder().id(1L).title("title1").author("author1").totalPages(2L)
				.requiredAge(3L).releaseDate(LocalDate.now()).price(100000).imgSrc("imgSrc1")
				.description("description1").build();

		bookUpdateRequestInitial = BookRequest.builder().id(1L).title("title1Updated").author("author1Updated")
				.totalPages(22L).requiredAge(33L).releaseDate(LocalDate.now()).price(900000).imgSrc("imgSrc1Updated")
				.description("description1Updated").build();

		bookUpdateRequestConvertToEntity = Book.builder().id(1L).title("title1Updated").author("author1Updated")
				.totalPages(22L).requiredAge(33L).releaseDate(LocalDate.now()).price(900000).imgSrc("imgSrc1Updated")
				.description("description1Updated").build();

		// expected first BookResponse
		expectedFirstBookResponse = BookAdminResponse.builder().id(1L).title("title1").author("author1").totalPages(2L)
				.requiredAge(3L).releaseDate(LocalDate.now()).price(100000).imgSrc("imgSrc1")
				.description("description1").ratingPoint(4.0).totalRatings(5L).createDate(new Date(100000L))
				.updateDate(new Date(200000L))
				.categories((new HashSet<Category>(List.of(categoryInitialFirst, categoryInitialSecond)))).build();

		// expected second BookResponse
		expectedSecondBookResponse = BookAdminResponse.builder().id(6L).title("title2").author("author2").totalPages(7L)
				.requiredAge(8L).releaseDate(LocalDate.now()).price(200000).imgSrc("imgSrc2")
				.description("description2").ratingPoint(9.0).totalRatings(10L).createDate(new Date(300000L))
				.updateDate(new Date(400000L))
				.categories((new HashSet<Category>(List.of(categoryInitialThird, categoryInitialFourth)))).build();

		// Add bookResponses to BookResponseList
		expectedListBookResponse = new ArrayList<>();
		expectedListBookResponse.add(expectedFirstBookResponse);
		expectedListBookResponse.add(expectedSecondBookResponse);
	}

	// UnitTest for function findBookById()
	@Test
	void findBookById_ShouldReturnBook_WhenIdValid() {

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(commonMapper.convertToResponse(expectedFirstBook, BookAdminResponse.class))
				.thenReturn(expectedFirstBookResponse);

		BookAdminResponse bookResponseResult = bookAdminServiceImpl.findBookById(1L);

		assertThat(bookResponseResult.equals(expectedFirstBookResponse), is(true));

		verify(bookRepository, times(1)).findById(1L);
		verify(commonMapper, times(1)).convertToResponse(expectedFirstBook, BookAdminResponse.class);
	}

	@Test
	void findBookById_ShouldThrowException_WhenIdInValid() {

		when(bookRepository.findById(expectedFirstBook.getId() + 1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookAdminServiceImpl.findBookById(expectedFirstBook.getId() + 1L));

		assertThat(exception.getMessage().equals("Book is not found!"), is(true));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId() + 1L);
	}

	// UnitTest for function findAllBooks()
	@Test
	void findAllBooks_ShouldReturnAllBooks() {

		when(bookRepository.findAll()).thenReturn(expectedBookList);
		when(commonMapper.convertToResponseList(expectedBookList, BookAdminResponse.class))
				.thenReturn(expectedListBookResponse);

		List<BookAdminResponse> bookResponseListResult = bookAdminServiceImpl.findAllBooks();
		bookResponseListResult.forEach(bookAdminResponse -> {
			assertThat(bookAdminResponse
					.equals(expectedListBookResponse.get(bookResponseListResult.indexOf(bookAdminResponse))), is(true));
		});

		assertThat(bookResponseListResult.size(), is(2));

		// Expect functions call
		verify(bookRepository, times(1)).findAll();
		verify(commonMapper, times(1)).convertToResponseList(expectedBookList, BookAdminResponse.class);
	}

	// UnitTest for function createBook()
	@Test
	void createBook_ShouldCreateBook_WhenTitleValid() {

		BookRequest bookRequestInitial = BookRequest.builder().id(1L).title("title1").author("author1").totalPages(2L)
				.requiredAge(3L).releaseDate(LocalDate.now()).price(100000).imgSrc("imgSrc1")
				.description("description1").build();

		BookAdminResponse expectedBookResponse = BookAdminResponse.builder().id(1L).title("title1").author("author1")
				.totalPages(2L).requiredAge(3L).releaseDate(LocalDate.now()).price(100000).imgSrc("imgSrc1")
				.description("description1").ratingPoint(4.0).totalRatings(5L).createDate(new Date(100000L))
				.updateDate(new Date(200000L)).build();

		when(bookRepository.findByTitle(bookRequestInitial.getTitle())).thenReturn(Optional.empty());
		when(commonMapper.convertToEntity(bookRequestInitial, Book.class)).thenReturn(expectedFirstBook);
		when(bookRepository.save(expectedFirstBook)).thenReturn(expectedFirstBook);
		when(commonMapper.convertToResponse(expectedFirstBook, BookAdminResponse.class))
				.thenReturn(expectedBookResponse);

		BookAdminResponse bookResponseResult = bookAdminServiceImpl.createBook(bookRequestInitial);

		assertThat(bookResponseResult.equals(expectedFirstBookResponse), is(true));

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

		assertThat(exception.getMessage()
				.equals("Title \"" + bookCreateRequestInitial.getTitle() + "\" is already existed!"), is(true));

		verify(bookRepository, times(1)).findByTitle(bookCreateRequestInitial.getTitle());
	}

	// UnitTest for function addCategoriesToBook()
	@Test
	void addCategoriesToBook_ShouldAddCategoriesToBook() {

		List<Long> newCategoriesIdToAdd = List.of(3L, 4L);
		List<Long> currentBookCategoriesId = List.of(1L, 2L);

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findAllIdsOfCategories(expectedFirstBook.getId())).thenReturn(currentBookCategoriesId);
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		String stringResult = bookAdminServiceImpl.addCategoriesToBook(expectedFirstBook.getId(), newCategoriesIdToAdd);

		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book savedBook = bookCaptor.getValue();

		assertThat(savedBook.getCategories().contains(categoryInitialThird), is(true));
		assertThat(savedBook.getCategories().contains(categoryInitialFourth), is(true));
		assertThat(stringResult.equals("Category successfully added."), is(true));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
		verify(bookRepository, times(1)).findAllIdsOfCategories(expectedFirstBook.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void addCategoriesToBook_ShouldThrowCategoryExistException_WhenCategoryAlreadyExistInBook() {

		List<Long> newCategoriesIdToAdd = List.of(2L, 3L);
		List<Long> currentBookCategoriesId = List.of(1L, 2L);

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findAllIdsOfCategories(expectedFirstBook.getId())).thenReturn(currentBookCategoriesId);
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		Exception exception = assertThrows(CategoryExistException.class,
				() -> bookAdminServiceImpl.addCategoriesToBook(expectedFirstBook.getId(), newCategoriesIdToAdd));
		assertThat(exception.getMessage(), is("Category with id: 2 is already existed in this book!"));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
		verify(bookRepository, times(1)).findAllIdsOfCategories(expectedFirstBook.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void addCategoriesToBook_ShouldThrowCategoryNotFoundException_WhenCategoryNotFound() {

		List<Long> newCategoriesIdToAdd = List.of(3L, 5L);
		List<Long> currentBookCategoriesId = List.of(1L, 2L);

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findAllIdsOfCategories(expectedFirstBook.getId())).thenReturn(currentBookCategoriesId);
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		Exception exception = assertThrows(CategoryNotFoundException.class,
				() -> bookAdminServiceImpl.addCategoriesToBook(expectedFirstBook.getId(), newCategoriesIdToAdd));
		assertThat(exception.getMessage(), is("Category with id: 5 is not found!"));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
		verify(bookRepository, times(1)).findAllIdsOfCategories(expectedFirstBook.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void addCategoriesToBook_ShouldThrowBookNotFoundException_WhenBookNotFound() {

		List<Long> newCategoriesIdToAdd = List.of(3L, 5L);

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.empty());

		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookAdminServiceImpl.addCategoriesToBook(expectedFirstBook.getId(), newCategoriesIdToAdd));
		assertThat(exception.getMessage(), is("Book with id: " + expectedFirstBook.getId() + " is not found!"));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
	}

	// UnitTest for function removeCategoriesFromBook()
	@Test
	void removeCategoriesFromBook_ShouldRemoveCategoriesFromBook() {

		List<Long> categoriesIdToRemove = List.of(1L, 2L);
		List<Long> currentBookCategoriesId = List.of(1L, 2L);

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findAllIdsOfCategories(expectedFirstBook.getId())).thenReturn(currentBookCategoriesId);
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		assertThat(expectedFirstBook.getCategories().contains(categoryInitialFirst), is(true));
		assertThat(expectedFirstBook.getCategories().contains(categoryInitialSecond), is(true));

		String stringResult = bookAdminServiceImpl.removeCategoriesFromBook(expectedFirstBook.getId(),
				categoriesIdToRemove);

		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book savedBook = bookCaptor.getValue();

		assertThat(savedBook.getCategories().contains(categoryInitialFirst), is(false));
		assertThat(savedBook.getCategories().contains(categoryInitialSecond), is(false));
		assertThat(stringResult.equals("Category successfully removed."), is(true));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
		verify(bookRepository, times(1)).findAllIdsOfCategories(expectedFirstBook.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void removeCategoriesFromBook_ShouldThrowCategoryNotFoundInBookException_WhenCategoryNotFoundInBook() {

		List<Long> categoriesIdToRemove = List.of(1L, 3L);
		List<Long> currentBookCategoriesId = List.of(1L, 2L);

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findAllIdsOfCategories(expectedFirstBook.getId())).thenReturn(currentBookCategoriesId);
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		Exception exception = assertThrows(CategoryNotFoundInBookException.class,
				() -> bookAdminServiceImpl.removeCategoriesFromBook(expectedFirstBook.getId(), categoriesIdToRemove));
		assertThat(exception.getMessage(), is("Category 3 is not found in this book!"));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
		verify(bookRepository, times(1)).findAllIdsOfCategories(expectedFirstBook.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void removeCategoriesFromBook_ShouldThrowCategoryNotFoundException_WhenCategoryNotFound() {

		List<Long> categoriesIdToRemove = List.of(1L, 5L);
		List<Long> currentBookCategoriesId = List.of(1L, 5L);

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findAllIdsOfCategories(expectedFirstBook.getId())).thenReturn(currentBookCategoriesId);
		when(categoryRepository.findAll()).thenReturn(categoryListInitial);

		Exception exception = assertThrows(CategoryNotFoundException.class,
				() -> bookAdminServiceImpl.removeCategoriesFromBook(expectedFirstBook.getId(), categoriesIdToRemove));
		assertThat(exception.getMessage(), is("Category with id: 5 is not found!"));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
		verify(bookRepository, times(1)).findAllIdsOfCategories(expectedFirstBook.getId());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void removeCategoriesFromBook_ShouldThrowBookNotFoundException_WhenBookNotFound() {

		List<Long> categoriesIdToRemove = List.of(1L, 5L);

		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.empty());

		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookAdminServiceImpl.removeCategoriesFromBook(expectedFirstBook.getId(), categoriesIdToRemove));
		assertThat(exception.getMessage(), is("Book with id: " + expectedFirstBook.getId() + " is not found!"));

		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
	}

	// UnitTest for function updateBook()
	@Test
	void updateBook_ShouldReturnUpdatedBook_WhenIdAndTitleValid() {

		bookUpdateRequestInitial = BookRequest.builder().id(1L).title("title1Updated").author("author1Updated")
				.totalPages(22L).requiredAge(33L).releaseDate(LocalDate.now()).price(900000).imgSrc("imgSrc1Updated")
				.description("description1Updated").build();

		when(bookRepository.findById(bookUpdateRequestInitial.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findByTitle(bookUpdateRequestInitial.getTitle())).thenReturn(Optional.empty());
		when(commonMapper.convertToEntity(bookUpdateRequestInitial, Book.class))
				.thenReturn(bookUpdateRequestConvertToEntity);
		when(bookRepository.save(any())).thenReturn(bookUpdateRequestConvertToEntity);
		when(commonMapper.convertToResponse(bookUpdateRequestConvertToEntity, BookAdminResponse.class))
				.thenReturn(expectedFirstBookResponse);

		BookAdminResponse BookResponseResult = bookAdminServiceImpl.updateBook(bookUpdateRequestInitial);

		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book savedBook = bookCaptor.getValue();

		assertThat(savedBook.getCategories(), is(expectedFirstBook.getCategories()));
		assertThat(savedBook.getCreateDate(), is(expectedFirstBook.getCreateDate()));
		assertThat(savedBook.getRatingPoint(), is(expectedFirstBook.getRatingPoint()));
		assertThat(savedBook.getTotalRatings(), is(expectedFirstBook.getTotalRatings()));

		assertThat(BookResponseResult.equals(expectedFirstBookResponse), is(true));

		verify(bookRepository, times(1)).findById(bookUpdateRequestInitial.getId());
		verify(bookRepository, times(1)).findByTitle(bookUpdateRequestInitial.getTitle());
		verify(commonMapper, times(1)).convertToEntity(bookUpdateRequestInitial, Book.class);
		verify(bookRepository, times(1)).save(any());
		verify(commonMapper, times(1)).convertToResponse(bookUpdateRequestConvertToEntity, BookAdminResponse.class);
	}

	@Test
	void updateBook_ShouldThrowBookExistException_WhenTitleAlreadyInUse() {

		bookUpdateRequestInitial = BookRequest.builder().id(1L).title("title1Updated").author("author1Updated")
				.totalPages(22L).requiredAge(33L).releaseDate(LocalDate.now()).price(900000).imgSrc("imgSrc1Updated")
				.description("description1Updated").build();

		when(bookRepository.findById(bookUpdateRequestInitial.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findByTitle(bookUpdateRequestInitial.getTitle()))
				.thenReturn(Optional.of(expectedSecondBook));

		Exception exception = assertThrows(BookExistException.class,
				() -> bookAdminServiceImpl.updateBook(bookUpdateRequestInitial));
		assertThat(exception.getMessage(),
				is("Title \"" + bookUpdateRequestInitial.getTitle() + "\" is already existed!"));

		verify(bookRepository, times(1)).findById(bookUpdateRequestInitial.getId());
		verify(bookRepository, times(1)).findByTitle(bookUpdateRequestInitial.getTitle());
	}

	@Test
	void updateBook_ShouldThrowBookNotFoundException_WhenBookNotFound() {

		bookUpdateRequestInitial = BookRequest.builder().id(1L).title("title1Updated").author("author1Updated")
				.totalPages(22L).requiredAge(33L).releaseDate(LocalDate.now()).price(900000).imgSrc("imgSrc1Updated")
				.description("description1Updated").build();

		when(bookRepository.findById(bookUpdateRequestInitial.getId())).thenReturn(Optional.empty());

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

		bookResponseListResult.forEach(bookResponse -> {
			assertThat(bookResponse.equals(expectedListBookResponse.get(bookResponseListResult.indexOf(bookResponse))),
					is(true));
		});
		assertThat(bookResponseListResult.size(), is(2));

		verify(categoryRepository, times(1)).findById(categoryInitialFirst.getId());
		verify(bookRepository, times(1)).findByCategory(categoryInitialFirst.getId());
		verify(commonMapper, times(1)).convertToResponseList(expectedBookList, BookAdminResponse.class);
	}

	// UnitTest for function findBooksByCategory()
	@Test
	void findBooksByCategory_ShouldThrowCategoryNotFoundException_WhenCategoryNotFound() {

		when(categoryRepository.findById(categoryInitialFirst.getId())).thenReturn(Optional.empty());

		Exception exception = assertThrows(CategoryNotFoundException.class,
				() -> bookAdminServiceImpl.findBooksByCategory(categoryInitialFirst.getId()));
		assertThat(exception.getMessage(), is("Category with id: " + categoryInitialFirst.getId() + " is not found!"));

		verify(categoryRepository, times(1)).findById(categoryInitialFirst.getId());
	}

	// UnitTest for function findBooksByCategory()
	@Test
	void findFeaturesBooks_ShouldReturndBookResponseList() {

		when(bookRepository.findFeaturesBooks()).thenReturn(expectedBookList);
		when(commonMapper.convertToResponseList(expectedBookList, BookAdminResponse.class))
				.thenReturn(expectedListBookResponse);

		List<BookAdminResponse> bookResponseListResult = bookAdminServiceImpl.findFeaturesBooks();

		bookResponseListResult.forEach(bookResponse -> {
			assertThat(bookResponse.equals(expectedListBookResponse.get(bookResponseListResult.indexOf(bookResponse))),
					is(true));
		});
		assertThat(bookResponseListResult.size(), is(2));

		verify(bookRepository, times(1)).findFeaturesBooks();
		verify(commonMapper, times(1)).convertToResponseList(expectedBookList, BookAdminResponse.class);
	}

}
