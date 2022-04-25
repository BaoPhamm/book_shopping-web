package com.springboot.shopping.service;

import java.util.List;

import com.springboot.shopping.dto.category.CategoryRequest;
import com.springboot.shopping.dto.category.CategoryResponse;

public interface CategoryService {

	CategoryResponse findCategoryById(Long categoryId);

	CategoryResponse findCategoryByName(String categoryName);

	List<CategoryResponse> findAllCategories();

	CategoryResponse createCategory(CategoryRequest categoryRequest);

	CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);

	List<CategoryResponse> deleteCategory(Long categoryId);

}
