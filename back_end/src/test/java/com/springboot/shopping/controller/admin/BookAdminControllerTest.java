package com.springboot.shopping.controller.admin;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.springboot.shopping.dto.book.AddCategoryToBookForm;
import com.springboot.shopping.dto.book.BookAdminResponse;
import com.springboot.shopping.dto.book.BookRequest;
import com.springboot.shopping.dto.book.RemoveCategoryFromBookForm;
import com.springboot.shopping.model.Category;
import com.springboot.shopping.service.book.BookAdminService;

class BookAdminControllerTest {

	private BookAdminController bookAdminController;
	private BookAdminService bookAdminService;
	private BookAdminResponse expectedFirstBookResponse;
	private BookAdminResponse expectedSecondBookResponse;
	private List<BookAdminResponse> expectedListBookResponse;
	private Category categoryInitialFirst;
	private Category categoryInitialSecond;
	private BookRequest bookRequestInitial;
	private AddCategoryToBookForm addCategoryToBookForm;
	private RemoveCategoryFromBookForm removeCategoryFromBookForm;

	@BeforeEach
	void beforeEach() {
		// Mock BookAdminService
		bookAdminService = mock(BookAdminService.class);

		bookAdminController = new BookAdminController(bookAdminService);

		categoryInitialFirst = Category.builder().id(1L).name("categoryName1").description("description1")
				.imgSrc("imgSrc1").build();
		categoryInitialSecond = Category.builder().id(2L).name("categoryName2").description("description2")
				.imgSrc("imgSrc2").build();

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
				.categories((new HashSet<Category>(List.of(categoryInitialFirst, categoryInitialSecond)))).build();

		// Add bookResponses to BookResponseList
		expectedListBookResponse = new ArrayList<>();
		expectedListBookResponse.add(expectedFirstBookResponse);
		expectedListBookResponse.add(expectedSecondBookResponse);

		// Init bookRequest Input
		bookRequestInitial = BookRequest.builder().id(1L).title("title1").author("author1").totalPages(2L)
				.requiredAge(3L).releaseDate(LocalDate.now()).price(100000).imgSrc("imgSrc1")
				.description("description1").build();

		addCategoryToBookForm = AddCategoryToBookForm.builder().bookId(1L).categoriesId(List.of(1L, 2L)).build();
		removeCategoryFromBookForm = RemoveCategoryFromBookForm.builder().bookId(1L).categoriesId(List.of(1L, 2L))
				.build();
	}

	// UnitTest for function getBookById()
	@Test
	void getBookById_ShouldReturnBookAdminResponse() {

		when(bookAdminService.findBookById(1L)).thenReturn(expectedFirstBookResponse);

		ResponseEntity<BookAdminResponse> response = bookAdminController.getBookById(1L);

		assertThat(response.getBody().equals(expectedFirstBookResponse), is(true));

		verify(bookAdminService, times(1)).findBookById(1L);
	}

	// UnitTest for function getAllBooks()
	@Test
	void getAllBooks_ShouldReturnListBookAdminResponse() {

		when(bookAdminService.findAllBooks()).thenReturn(expectedListBookResponse);

		ResponseEntity<List<BookAdminResponse>> response = bookAdminController.getAllBooks();

		assertThat(response.getBody().equals(expectedListBookResponse), is(true));

		verify(bookAdminService, times(1)).findAllBooks();
	}

	// UnitTest for function getFeaturesBooks()
	@Test
	void getFeaturesBooks_ShouldReturnListBookAdminResponse() {

		when(bookAdminService.findFeaturesBooks()).thenReturn(expectedListBookResponse);

		ResponseEntity<List<BookAdminResponse>> response = bookAdminController.getFeaturesBooks();

		assertThat(response.getBody().equals(expectedListBookResponse), is(true));

		verify(bookAdminService, times(1)).findFeaturesBooks();
	}

	// UnitTest for function getBooksByCategory()
	@Test
	void getBooksByCategory_ShouldReturnListBookAdminResponse() {

		when(bookAdminService.findBooksByCategory(1L)).thenReturn(expectedListBookResponse);

		ResponseEntity<List<BookAdminResponse>> response = bookAdminController.getBooksByCategory(1L);

		assertThat(response.getBody().equals(expectedListBookResponse), is(true));

		verify(bookAdminService, times(1)).findBooksByCategory(1L);
	}

	// UnitTest for function saveBook()
	@Test
	void saveBook_ShouldReturnBookAdminResponse() {

		when(bookAdminService.createBook(bookRequestInitial)).thenReturn(expectedFirstBookResponse);

		ResponseEntity<BookAdminResponse> response = bookAdminController.saveBook(bookRequestInitial);

		assertThat(response.getBody().equals(expectedFirstBookResponse), is(true));

		verify(bookAdminService, times(1)).createBook(bookRequestInitial);
	}

	// UnitTest for function addCategoriesToBook()
	@Test
	void addCategoriesToBook_ShouldReturnSuccessMessgae() {

		String messageReturn = "Succes message!";
		when(bookAdminService.addCategoriesToBook(addCategoryToBookForm.getBookId(),
				addCategoryToBookForm.getCategoriesId())).thenReturn(messageReturn);

		ResponseEntity<String> messageResult = bookAdminController.addCategoriesToBook(addCategoryToBookForm);

		assertThat(messageResult.getBody(), is(messageReturn));

		verify(bookAdminService, times(1)).addCategoriesToBook(addCategoryToBookForm.getBookId(),
				addCategoryToBookForm.getCategoriesId());
	}

	// UnitTest for function removeCategoriesFromBook()
	@Test
	void removeCategoriesFromBook_ShouldReturnSuccessMessgae() {

		String messageReturn = "Succes message!";
		when(bookAdminService.removeCategoriesFromBook(removeCategoryFromBookForm.getBookId(),
				removeCategoryFromBookForm.getCategoriesId())).thenReturn(messageReturn);

		ResponseEntity<String> messageResult = bookAdminController.removeCategoriesFromBook(removeCategoryFromBookForm);

		assertThat(messageResult.getBody(), is(messageReturn));

		verify(bookAdminService, times(1)).removeCategoriesFromBook(removeCategoryFromBookForm.getBookId(),
				removeCategoryFromBookForm.getCategoriesId());
	}

	// UnitTest for function updateBook()
	@Test
	void updateBook_ShouldReturnBookAdminResponse() {

		when(bookAdminService.updateBook(bookRequestInitial)).thenReturn(expectedFirstBookResponse);

		ResponseEntity<BookAdminResponse> response = bookAdminController.updateBook(bookRequestInitial);

		assertThat(response.getBody().equals(expectedFirstBookResponse), is(true));

		verify(bookAdminService, times(1)).updateBook(bookRequestInitial);
	}

	// UnitTest for function deleteBook()
	@Test
	void deleteBook_ShouldReturnBookAdminResponse() {

		String messageReturn = "Succes message!";

		when(bookAdminService.deleteBook(1L)).thenReturn(messageReturn);

		ResponseEntity<String> messageResult = bookAdminController.deleteBook(1L);

		assertThat(messageResult.getBody().equals(messageReturn), is(true));

		verify(bookAdminService, times(1)).deleteBook(1L);
	}

}
