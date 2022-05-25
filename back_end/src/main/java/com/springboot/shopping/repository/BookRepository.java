package com.springboot.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Query(value = "select * from books b order by b.rating_point DESC limit 3" ,nativeQuery = true)
	List<Book> findFeaturesBooks();
	
	@Query(value = "select count(*) from book_rating br where br.book_id = :bookId",nativeQuery = true)
	Long getTotalRatings(Long bookId);

	Optional<Book> findByTitle(String bookTitle);
	
	Page<Book> findAll(Pageable pageable);
	
	@Query(value = "select count(b.id) from books b "
			+ "inner join books_categories bc on b.id = bc.book_id "
			+ "where bc.categories_id = :categoryId" ,nativeQuery = true)
	Long getTotalBooksByCategory(Long categoryId);

}
