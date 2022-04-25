package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.shopping.exception.ApiRequestException;
import com.springboot.shopping.model.Category;
import com.springboot.shopping.repository.CategoryRepository;
import com.springboot.shopping.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public Optional<Category> findCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId);
	}

	@Override
	public List<Category> findAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Long categoryId, Category category) {
		categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ApiRequestException("Category not found!", HttpStatus.NOT_FOUND));
		category.setId(categoryId);
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> deleteCategory(Long categoryId) {
		categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ApiRequestException("Category not found!", HttpStatus.NOT_FOUND));
		categoryRepository.deleteById(categoryId);
		return categoryRepository.findAll();
	}

}
