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
			+ "inner join categories c on c.id = bc.categories_id "
			+ "where c.name = :categoryName" ,nativeQuery = true)
	List<Book> findByCategory(String categoryName);

	Optional<Book> findByTitle(String bookTitle);

}
