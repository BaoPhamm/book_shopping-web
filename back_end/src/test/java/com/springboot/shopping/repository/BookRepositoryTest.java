package com.springboot.shopping.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springboot.shopping.model.Book;
import com.springboot.shopping.model.BookRating;

@DataJpaTest
class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private RatingRepository ratingRepository;

	// UnitTest for function findById()
	@Test
	void findById_ShouldReturnExistBook() {

		Book bookInDb = bookRepository.save(Book.builder().id(1L).title("title").build());
		Optional<Book> bookFound = bookRepository.findById(bookInDb.getId());

		assertThat(bookFound.get().getId(), is(bookInDb.getId()));
		assertThat(bookFound.get().getTitle(), is(bookInDb.getTitle()));
	}

	@Test
	void findById_ShouldReturnEmptyOptional() {

		Book bookInDb = bookRepository.save(Book.builder().id(1L).title("title").build());
		Optional<Book> bookFound = bookRepository.findById(bookInDb.getId() + 1L);

		assertThat(bookFound.isEmpty(), is(true));
	}

	@Test
	// UnitTest for function getTotalRatings()
	void getTotalRatings_ShouldReturnTotalRatings() {
		Book bookInDb = bookRepository.save(Book.builder().id(1L).title("title").build());
		ratingRepository.save(BookRating.builder().id(1L).book(bookInDb).build());
		ratingRepository.save(BookRating.builder().id(2L).book(bookInDb).build());

		Long TotalRatingsFound = bookRepository.getTotalRatings(bookInDb.getId());

		assertThat(TotalRatingsFound, is(2L));
	}

	@Test
	// UnitTest for function findFeaturesBooks()
	void findFeaturesBooks_ShouldReturnFeatureBooks() {

		Book bookInDbFirst = bookRepository
				.save(Book.builder().id(1L).title("title1").ratingPoint(Double.valueOf(2)).build());
		Book bookInDbSecond = bookRepository
				.save(Book.builder().id(2L).title("title2").ratingPoint(Double.valueOf(5)).build());
		Book bookInDbThird = bookRepository
				.save(Book.builder().id(3L).title("title3").ratingPoint(Double.valueOf(3)).build());
		Book bookInDbFourth = bookRepository
				.save(Book.builder().id(4L).title("title4").ratingPoint(Double.valueOf(4)).build());

		List<Book> listFeatureBookFound = bookRepository.findFeaturesBooks();

		assertThat(listFeatureBookFound.size(), is(3));
		assertThat(listFeatureBookFound.get(0), is(bookInDbSecond));
		assertThat(listFeatureBookFound.get(1), is(bookInDbFourth));
		assertThat(listFeatureBookFound.get(2), is(bookInDbThird));
	}

}
