package com.springboot.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.shopping.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	@Query(value = "select * from books b "
			+ "inner join books_categories bc on b.id = bc.book_id "
			+ "where bc.categories_id = :categoryId" ,nativeQuery = true)
	List<Book> findByCategory(Long categoryId);
	
	@Query(value = "select bc.categories_id from books b "
			+ "inner join books_categories bc on b.id = bc.book_id "
			+ "where bc.book_id = :bookId" ,nativeQuery = true)
	List<Long> findAllIdsOfCategories(Long bookId);
	
	@Query(value = "select * from books b "
			+ "limit 3 ",nativeQuery = true)
	List<Book> findFeaturesBooks();

	Optional<Book> findByTitle(String bookTitle);

}
