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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.BookResponse;
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
import com.springboot.shopping.service.impl.BookServiceImpl;

class BookServiceTest {

	private BookServiceImpl bookServiceImpl;
	private BookRepository bookRepository;
	private CategoryRepository categoryRepository;
	private CommonMapper commonMapper;
	private BookRequest bookRequestInitial;
	private BookResponse expectedFirstBookResponse;
	private BookResponse expectedSecondBookResponse;
	private List<BookResponse> expectedListBookResponse;

	private Book expectedFirstBook;
	private Book expectedSecondBook;
	private List<Book> expectedBookList;
	private Collection<Category> categoryListInitial;
	private Category categoryInitial;

	@BeforeEach
	void beforeEach() {
		// Mock BookRepository
		bookRepository = mock(BookRepository.class);
		// Mock CategoryRepository
		categoryRepository = mock(CategoryRepository.class);
		// Mock CommonMapper
		commonMapper = mock(CommonMapper.class);
		bookServiceImpl = new BookServiceImpl(bookRepository, categoryRepository, commonMapper);

		// right category list for book
		categoryListInitial = new ArrayList<>();
		categoryInitial = Category.builder().id(1L).name("Initial_category_name").build();
		categoryListInitial.add(categoryInitial);

		// expected first Book
		expectedFirstBook = Book.builder().id(1L).title("ABCXYZ").author("baobao").totalPages(100).requiredAge(18)
				.releaseDate(LocalDate.now()).price(100000).imgSrc("https://www.google.com").description("nothing")
				.categories(categoryListInitial).build();

		// expected second Book
		expectedSecondBook = Book.builder().id(2L).title("QWERTY").author("baopham").totalPages(200).requiredAge(8)
				.releaseDate(LocalDate.now()).price(200000).imgSrc("https://www.google.com").description("nothing too")
				.build();

		// Add books to BookList
		expectedBookList = new ArrayList<>();
		expectedBookList.add(expectedFirstBook);
		expectedBookList.add(expectedSecondBook);

		// Init bookRequest Input
		bookRequestInitial = BookRequest.builder().title("ABCXYZ").author("baobao").totalPages(100).requiredAge(18)
				.releaseDate(LocalDate.now()).price(100000).imgSrc("https://www.google.com").description("nothing")
				.build();

		// expected first BookResponse
		expectedFirstBookResponse = BookResponse.builder().id(1L).title("ABCXYZ").author("baobao").totalPages(100)
				.requiredAge(18).releaseDate(LocalDate.now()).price(100000).imgSrc("https://www.google.com")
				.description("nothing").build();

		// expected second BookResponse
		expectedSecondBookResponse = BookResponse.builder().id(2L).title("QWERTY").author("baopham").totalPages(200)
				.requiredAge(8).releaseDate(LocalDate.now()).price(200000).imgSrc("https://www.google.com")
				.description("nothing too").build();

		// Add bookResponses to BookResponseList
		expectedListBookResponse = new ArrayList<>();
		expectedListBookResponse.add(expectedFirstBookResponse);
		expectedListBookResponse.add(expectedSecondBookResponse);
	}

	// UnitTest for function findBookById()
	@Test
	void findBookById_ShouldReturnBook_WhenIdValid() {
		// Set return values for mock functions
		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(commonMapper.convertToResponse(expectedFirstBook, BookResponse.class))
				.thenReturn(expectedFirstBookResponse);

		// Call function findBookById()
		BookResponse bookResponseResult = bookServiceImpl.findBookById(1L);
		// Compare return value and expected value
		assertThat(bookResponseResult.equals(expectedFirstBookResponse), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findById(1L);
		verify(commonMapper, times(1)).convertToResponse(expectedFirstBook, BookResponse.class);
	}

	@Test
	void findBookById_ShouldThrowException_WhenIdInValid() {
		// Set return values for mock functions
		when(bookRepository.findById(expectedFirstBook.getId() + 2L)).thenReturn(Optional.empty());

		// Call function findBookById()
		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookServiceImpl.findBookById(expectedFirstBook.getId() + 2L));

		// Compare return value and expected value
		assertThat(exception.getMessage().equals("Book is not found!"), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findById(expectedFirstBook.getId() + 2L);
	}

	// UnitTest for function findAllBooks()
	@Test
	void findAllBooks_ShouldReturnAllBooks() {
		// Set return values for mock functions
		when(bookRepository.findAll()).thenReturn(expectedBookList);
		when(commonMapper.convertToResponseList(expectedBookList, BookResponse.class))
				.thenReturn(expectedListBookResponse);

		// Call function findAllBooks()
		List<BookResponse> bookResponseListResult = bookServiceImpl.findAllBooks();
		bookResponseListResult.forEach(bookResponse -> {
			assertThat(bookResponse.equals(expectedListBookResponse.get(bookResponseListResult.indexOf(bookResponse))),
					is(true));
		});

		// Expect bookResponse List has 2 object as initial
		assertThat(bookResponseListResult.size(), is(2));

		// Expect functions call
		verify(bookRepository, times(1)).findAll();
		verify(commonMapper, times(1)).convertToResponseList(expectedBookList, BookResponse.class);
	}

	// UnitTest for function createBook()
	@Test
	void createBook_ShouldCreateBook_WhenTitleValid() {

		// Init bookRequest Input
		BookRequest bookRequestInitial = BookRequest.builder().title("ABCXYZ").author("baobao").totalPages(100)
				.requiredAge(18).releaseDate(LocalDate.now()).price(100000).imgSrc("https://www.google.com")
				.description("nothing").build();

		// Set return values for mock functions
		when(bookRepository.findByTitle(bookRequestInitial.getTitle())).thenReturn(Optional.empty());
		when(commonMapper.convertToEntity(bookRequestInitial, Book.class)).thenReturn(expectedFirstBook);
		when(bookRepository.save(expectedFirstBook)).thenReturn(expectedFirstBook);
		when(commonMapper.convertToResponse(expectedFirstBook, BookResponse.class))
				.thenReturn(expectedFirstBookResponse);

		// Call function createBook()
		BookResponse bookResponseResult = bookServiceImpl.createBook(bookRequestInitial);

		// Check output
		assertThat(bookResponseResult.equals(expectedFirstBookResponse), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(bookRequestInitial.getTitle());
		verify(commonMapper, times(1)).convertToEntity(bookRequestInitial, Book.class);
		verify(bookRepository, times(1)).save(expectedFirstBook);
		verify(commonMapper, times(1)).convertToResponse(expectedFirstBook, BookResponse.class);
	}

	@Test
	void createBook_ShouldThrowBookExistException_WhenTitleInValid() {

		// Set return values for mock functions
		when(bookRepository.findByTitle(bookRequestInitial.getTitle())).thenReturn(Optional.of(expectedFirstBook));

		// Call function createBook()
		Exception exception = assertThrows(BookExistException.class,
				() -> bookServiceImpl.createBook(bookRequestInitial));

		// Check output
		assertThat(exception.getMessage().equals("Book is already existed!"), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(bookRequestInitial.getTitle());
	}

	// UnitTest for function addCategoryToBook()
	@Test
	void addCategoryToBook_ShouldAddCategoryToBook() {

		// Init category need to add
		Category newCategory = Category.builder().id(2L).name("Category_name").build();

		// Set return values for mock functions
		when(bookRepository.findByTitle(expectedFirstBook.getTitle())).thenReturn(Optional.of(expectedFirstBook));
		when(categoryRepository.findByName(newCategory.getName())).thenReturn(Optional.of(newCategory));
		when(bookRepository.save(expectedFirstBook)).thenReturn(expectedFirstBook);

		// Call function addCategoryToBook()
		String stringResult = bookServiceImpl.addCategoryToBook(expectedFirstBook.getTitle(), newCategory.getName());

		// Get Book input of function save()
		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book savedBook = bookCaptor.getValue();

		// Check output
		assertThat(savedBook.equals(expectedFirstBook), is(true));
		assertThat(stringResult.equals("Category successfully added."), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(expectedFirstBook.getTitle());
		verify(categoryRepository, times(1)).findByName(newCategory.getName());
		verify(bookRepository, times(1)).save(expectedFirstBook);
	}

	@Test
	void addCategoryToBook_ShouldThrowCategoryExistException_WhenCategoryAlreadyExistIBook() {

		// Set return values for mock functions
		when(bookRepository.findByTitle(expectedFirstBook.getTitle())).thenReturn(Optional.of(expectedFirstBook));
		when(categoryRepository.findByName(categoryInitial.getName())).thenReturn(Optional.of(categoryInitial));

		// Call function addCategoryToBook()
		Exception exception = assertThrows(CategoryExistException.class,
				() -> bookServiceImpl.addCategoryToBook(expectedFirstBook.getTitle(), categoryInitial.getName()));

		// Check output
		assertThat(exception.getMessage().equals("Category is already existed!"), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(expectedFirstBook.getTitle());
		verify(categoryRepository, times(1)).findByName(categoryInitial.getName());
	}

	@Test
	void addCategoryToBook_ShouldThrowCategoryNotFoundException_WhenCategoryNotFound() {

		// Set return values for mock functions
		when(bookRepository.findByTitle(expectedFirstBook.getTitle())).thenReturn(Optional.of(expectedFirstBook));
		when(categoryRepository.findByName(categoryInitial.getName())).thenReturn(Optional.empty());

		// Call function addCategoryToBook()
		Exception exception = assertThrows(CategoryNotFoundException.class,
				() -> bookServiceImpl.addCategoryToBook(expectedFirstBook.getTitle(), categoryInitial.getName()));

		// Check output
		assertThat(exception.getMessage().equals("Category is not found!"), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(expectedFirstBook.getTitle());
		verify(categoryRepository, times(1)).findByName(categoryInitial.getName());
	}

	@Test
	void addCategoryToBook_ShouldThrowBookNotFoundException_WhenBookNotFound() {

		// Set return values for mock functions
		when(bookRepository.findByTitle(expectedFirstBook.getTitle())).thenReturn(Optional.empty());

		// Call function addCategoryToBook()
		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookServiceImpl.addCategoryToBook(expectedFirstBook.getTitle(), categoryInitial.getName()));

		// Check output
		assertThat(exception.getMessage().equals("Book is not found!"), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(expectedFirstBook.getTitle());
	}

	// UnitTest for function removeCategoryFromBook()
	@Test
	void addCategoryToBook_ShouldRemoveCategoryFromBook() {

		// Set return values for mock functions
		when(bookRepository.findByTitle(expectedFirstBook.getTitle())).thenReturn(Optional.of(expectedFirstBook));
		when(categoryRepository.findByName(categoryInitial.getName())).thenReturn(Optional.of(categoryInitial));
		when(bookRepository.save(expectedFirstBook)).thenReturn(expectedFirstBook);

		// Call function removeCategoryFromBook()
		String stringResult = bookServiceImpl.removeCategoryFromBook(expectedFirstBook.getTitle(),
				categoryInitial.getName());

		// Get Book input of function save()
		ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookCaptor.capture());
		Book savedBook = bookCaptor.getValue();

		// Check output
		assertThat(savedBook.equals(expectedFirstBook), is(true));
		assertThat(stringResult.equals("Category successfully removed."), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(expectedFirstBook.getTitle());
		verify(categoryRepository, times(1)).findByName(categoryInitial.getName());
		verify(bookRepository, times(1)).save(expectedFirstBook);
	}

	@Test
	void addCategoryToBook_ShouldRThrowCategoryNotFoundInBookException_WhenCategoryNotFoundInBook() {

		// Init category need to remove
		Category newCategory = Category.builder().id(1L).name("Category_name").build();

		// Set return values for mock functions
		when(bookRepository.findByTitle(expectedFirstBook.getTitle())).thenReturn(Optional.of(expectedFirstBook));
		when(categoryRepository.findByName(newCategory.getName())).thenReturn(Optional.of(newCategory));

		// Call function removeCategoryFromBook()
		Exception exception = assertThrows(CategoryNotFoundInBookException.class,
				() -> bookServiceImpl.removeCategoryFromBook(expectedFirstBook.getTitle(), newCategory.getName()));

		// Check output
		assertThat(exception.getMessage().equals("Category is not found in this book!"), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(expectedFirstBook.getTitle());
		verify(categoryRepository, times(1)).findByName(newCategory.getName());
	}

	@Test
	void addCategoryToBook_ShouldRThrowCategoryNotFoundException_WhenCategoryNotFound() {

		// Init category need to remove
		Category newCategory = Category.builder().id(1L).name("Category_name").build();

		// Set return values for mock functions
		when(bookRepository.findByTitle(expectedFirstBook.getTitle())).thenReturn(Optional.of(expectedFirstBook));
		when(categoryRepository.findByName(newCategory.getName())).thenReturn(Optional.empty());

		// Call function removeCategoryFromBook()
		Exception exception = assertThrows(CategoryNotFoundException.class,
				() -> bookServiceImpl.removeCategoryFromBook(expectedFirstBook.getTitle(), newCategory.getName()));

		// Check output
		assertThat(exception.getMessage().equals("Category is not found!"), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(expectedFirstBook.getTitle());
		verify(categoryRepository, times(1)).findByName(newCategory.getName());
	}

	@Test
	void addCategoryToBook_ShouldRThrowBookNotFoundException_WhenBookNotFound() {

		// Init category need to remove
		Category newCategory = Category.builder().id(1L).name("Category_name").build();

		// Set return values for mock functions
		when(bookRepository.findByTitle(expectedFirstBook.getTitle())).thenReturn(Optional.empty());

		// Call function removeCategoryFromBook()
		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookServiceImpl.removeCategoryFromBook(expectedFirstBook.getTitle(), newCategory.getName()));

		// Check output
		assertThat(exception.getMessage().equals("Book is not found!"), is(true));

		// Expect functions call
		verify(bookRepository, times(1)).findByTitle(expectedFirstBook.getTitle());
	}

	// UnitTest for function updateBook()
	@Test
	void updateBook_ShouldReturnUpdatedBook_WhenIdValid() {

		// Init bookRequest Input
		BookRequest bookRequestInitial = new BookRequest();

		// Set return values for mock functions
		when(bookRepository.findById(expectedSecondBook.getId())).thenReturn(Optional.of(expectedSecondBook));
		when(bookRepository.save(any())).thenReturn(expectedFirstBook);
		when(commonMapper.convertToResponse(expectedFirstBook, BookResponse.class))
				.thenReturn(expectedFirstBookResponse);

		// Call function updateBook()
		BookResponse BookResponseResult = bookServiceImpl.updateBook(expectedSecondBook.getId(), bookRequestInitial);

		// Check output
		assertThat(BookResponseResult, is(expectedFirstBookResponse));

		// Expect functions call
		verify(bookRepository, times(1)).findById(expectedSecondBook.getId());
		verify(bookRepository, times(1)).save(any());
		verify(commonMapper, times(1)).convertToResponse(expectedFirstBook, BookResponse.class);
	}

	@Test
	void updateBook_ShouldBookNotFoundException_WhenBookNotFound() {

		// Init bookRequest Input
		BookRequest bookRequestInitial = new BookRequest();

		// Set return values for mock functions
		when(bookRepository.findById(expectedSecondBook.getId())).thenReturn(Optional.empty());

		// Call function updateBook()
		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookServiceImpl.updateBook(expectedSecondBook.getId(), bookRequestInitial));

		// Check output
		assertThat(exception.getMessage(), is("Book is not found!"));

		// Expect functions call
		verify(bookRepository, times(1)).findById(expectedSecondBook.getId());
	}

	// UnitTest for function deleteBook()
	@Test
	void deleteBook_ShouldReturnUpdatedBookResponseList_WhenIdValid() {

		// Set return values for mock functions
		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.of(expectedFirstBook));
		when(bookRepository.findAll()).thenReturn(expectedBookList);
		when(commonMapper.convertToResponseList(expectedBookList, BookResponse.class))
				.thenReturn(expectedListBookResponse);

		// Call function deleteBook()
		List<BookResponse> bookResponseListResult = bookServiceImpl.deleteBook(expectedFirstBook.getId());

		// Check output
		bookResponseListResult.forEach(bookResponse -> {
			assertThat(bookResponse.equals(expectedListBookResponse.get(bookResponseListResult.indexOf(bookResponse))),
					is(true));
		});
		assertThat(bookResponseListResult.size(), is(2));

		// Expect functions call
		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
		verify(bookRepository, times(1)).deleteById(expectedFirstBook.getId());
		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void deleteBook_ShouldBookNotFoundException_WhenBookNotFound() {

		// Set return values for mock functions
		when(bookRepository.findById(expectedFirstBook.getId())).thenReturn(Optional.empty());

		// Call function updateBook()
		Exception exception = assertThrows(BookNotFoundException.class,
				() -> bookServiceImpl.deleteBook(expectedFirstBook.getId()));

		// Check output
		assertThat(exception.getMessage(), is("Book is not found!"));

		// Expect functions call
		verify(bookRepository, times(1)).findById(expectedFirstBook.getId());
	}

	// UnitTest for function findBooksByCategory()
	@Test
	void findBooksByCategory_ShouldReturndBookResponseList_WhenCategoryNameValid() {

		// Set return values for mock functions
		when(categoryRepository.findByName(categoryInitial.getName())).thenReturn(Optional.of(categoryInitial));
		when(bookRepository.findByCategory(categoryInitial.getName())).thenReturn(expectedBookList);
		when(commonMapper.convertToResponseList(expectedBookList, BookResponse.class))
				.thenReturn(expectedListBookResponse);

		// Call function findBooksByCategory()
		List<BookResponse> bookResponseListResult = bookServiceImpl.findBooksByCategory(categoryInitial.getName());

		// Check output
		bookResponseListResult.forEach(bookResponse -> {
			assertThat(bookResponse.equals(expectedListBookResponse.get(bookResponseListResult.indexOf(bookResponse))),
					is(true));
		});
		assertThat(bookResponseListResult.size(), is(2));

		// Expect functions call
		verify(categoryRepository, times(1)).findByName(categoryInitial.getName());
		verify(bookRepository, times(1)).findByCategory(categoryInitial.getName());
		verify(commonMapper, times(1)).convertToResponseList(expectedBookList, BookResponse.class);
	}

	@Test
	void findBooksByCategory_ShouldThrowCategoryNotFoundException_WhenCategoryNotFound() {

		// Set return values for mock functions
		when(categoryRepository.findByName(categoryInitial.getName())).thenReturn(Optional.empty());

		// Call function findBooksByCategory()\
		Exception exception = assertThrows(CategoryNotFoundException.class,
				() -> bookServiceImpl.findBooksByCategory(categoryInitial.getName()));

		// Check output
		assertThat(exception.getMessage(), is("Category is not found!"));

		// Expect functions call
		verify(categoryRepository, times(1)).findByName(categoryInitial.getName());
	}

}
