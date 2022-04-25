package com.springboot.shopping.service;

import java.util.List;
import java.util.Optional;

import com.springboot.shopping.model.Category;

public interface CategoryService {
	
	Optional<Category> findCategoryById(Long categoryId);
	
	Optional<Category> findCategoryByName(String categoryName);

	List<Category> findAllCategories();
	
	Category createCategory(Category category);

	Category updateCategory(Long categoryId, Category category);

	List<Category> deleteCategory(Long categoryId);

}
