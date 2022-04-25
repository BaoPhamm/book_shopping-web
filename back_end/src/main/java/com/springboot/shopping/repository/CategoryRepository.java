package com.springboot.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.shopping.model.Book;
import com.springboot.shopping.model.Category;
import com.springboot.shopping.model.UserEntity;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Optional<Category> findByName(String categoryName);

}
