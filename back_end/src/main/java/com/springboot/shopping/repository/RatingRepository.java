package com.springboot.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.shopping.model.BookRating;

@Repository
public interface RatingRepository extends JpaRepository<BookRating, Long> {

	@Query(value = "select * from book_rating br "
			+ "where br.book_id = :bookId "
			+ "and br.user_id = :userId", nativeQuery = true)
	Optional<BookRating> findExistUserBookRating(Long userId, Long bookId);
}
