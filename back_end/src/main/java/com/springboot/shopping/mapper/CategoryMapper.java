package com.springboot.shopping.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.shopping.dto.category.CategoryRequest;
import com.springboot.shopping.dto.category.CategoryResponse;
import com.springboot.shopping.model.Category;
import com.springboot.shopping.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

	private final CommonMapper commonMapper;
	private final CategoryService categoryService;

	public CategoryResponse findCategoryById(Long categoryId) {
		return commonMapper.convertToResponse(categoryService.findCategoryById(categoryId).get(),
				CategoryResponse.class);
	}

	public CategoryResponse findCategoryByName(String categoryName) {
		return commonMapper.convertToResponse(categoryService.findCategoryByName(categoryName).get(),
				CategoryResponse.class);
	}

	public List<CategoryResponse> findAllCategories() {
		return commonMapper.convertToResponseList(categoryService.findAllCategories(), CategoryResponse.class);
	}

	public CategoryResponse createCategory(CategoryRequest categoryRequest) {
		Category category = commonMapper.convertToEntity(categoryRequest, Category.class);
		return commonMapper.convertToResponse(categoryService.createCategory(category), CategoryResponse.class);
	}

	public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
		Category category = commonMapper.convertToEntity(categoryRequest, Category.class);
		return commonMapper.convertToResponse(categoryService.updateCategory(categoryId, category),
				CategoryResponse.class);
	}

	public List<CategoryResponse> deleteCategory(Long categoryId) {
		return commonMapper.convertToResponseList(categoryService.deleteCategory(categoryId), CategoryResponse.class);
	}

}
