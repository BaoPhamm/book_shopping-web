package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.shopping.dto.category.CategoryRequest;
import com.springboot.shopping.dto.category.CategoryResponse;
import com.springboot.shopping.exception.category.CategoryExistException;
import com.springboot.shopping.exception.category.CategoryNotFoundException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Category;
import com.springboot.shopping.repository.CategoryRepository;
import com.springboot.shopping.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CommonMapper commonMapper;

	@Override
	public CategoryResponse findCategoryById(Long categoryId) {
		Optional<Category> categoryFromDb = categoryRepository.findById(categoryId);
		if (categoryFromDb.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		return commonMapper.convertToResponse(categoryFromDb.get(), CategoryResponse.class);
	}

	@Override
	public CategoryResponse findCategoryByName(String categoryName) {
		Optional<Category> categoryFromDb = categoryRepository.findByName(categoryName);
		if (categoryFromDb.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		return commonMapper.convertToResponse(categoryFromDb.get(), CategoryResponse.class);
	}

	@Override
	public List<CategoryResponse> findAllCategories() {
		return commonMapper.convertToResponseList(categoryRepository.findAll(), CategoryResponse.class);
	}

	@Override
	public CategoryResponse createCategory(CategoryRequest categoryRequest) {
		Category newCategory = commonMapper.convertToEntity(categoryRequest, Category.class);
		Optional<Category> categoryFromDb = categoryRepository.findByName(categoryRequest.getName());
		if (categoryFromDb.isPresent()) {
			throw new CategoryExistException();
		}
		return commonMapper.convertToResponse(categoryRepository.save(newCategory), CategoryResponse.class);
	}

	@Override
	public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {

		Category newCategoryInfo = commonMapper.convertToEntity(categoryRequest, Category.class);
		Optional<Category> categoryFromDb = categoryRepository.findById(categoryId);
		if (categoryFromDb.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		categoryFromDb.get().setName(newCategoryInfo.getName());
		return commonMapper.convertToResponse(categoryRepository.save(categoryFromDb.get()), CategoryResponse.class);
	}

	@Override
	public List<CategoryResponse> deleteCategory(Long categoryId) {

		Optional<Category> categoryFromDb = categoryRepository.findById(categoryId);
		if (categoryFromDb.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		categoryRepository.deleteById(categoryId);
		return commonMapper.convertToResponseList(categoryRepository.findAll(), CategoryResponse.class);
	}

}
