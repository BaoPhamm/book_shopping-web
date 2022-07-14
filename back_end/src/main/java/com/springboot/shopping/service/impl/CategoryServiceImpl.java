package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		Category categoryFromDb = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException());
		return commonMapper.convertToResponse(categoryFromDb, CategoryResponse.class);
	}

	@Override
	public CategoryResponse findCategoryByName(String categoryName) {
		Category categoryFromDb = categoryRepository.findByName(categoryName)
				.orElseThrow(() -> new CategoryNotFoundException());
		return commonMapper.convertToResponse(categoryFromDb, CategoryResponse.class);
	}

	@Override
	public List<CategoryResponse> findAllCategories() {
		return commonMapper.convertToResponseList(categoryRepository.findAll(), CategoryResponse.class);
	}

	@Override
	public CategoryResponse createCategory(CategoryRequest categoryRequest) {
		Optional<Category> categoryFromDb = categoryRepository.findByName(categoryRequest.getName());
		if (categoryFromDb.isPresent()) {
			throw new CategoryExistException();
		}
		Category newCategory = commonMapper.convertToEntity(categoryRequest, Category.class);
		Category savedCategory = categoryRepository.save(newCategory);
		return commonMapper.convertToResponse(savedCategory, CategoryResponse.class);
	}

	@Override
	public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
		Category categoryFromDb = categoryRepository.findById(categoryRequest.getId())
				.orElseThrow(() -> new CategoryNotFoundException());
		categoryFromDb.setName(categoryRequest.getName());
		categoryFromDb.setDescription(categoryRequest.getDescription());
		categoryFromDb.setImgSrc(categoryRequest.getImgSrc());
		Category updatedCategory = categoryRepository.save(categoryFromDb);
		return commonMapper.convertToResponse(updatedCategory, CategoryResponse.class);
	}

	@Override
	@Transactional
	public String deleteCategory(Long categoryId) {
		categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException());
		categoryRepository.deleteById(categoryId);
		return "Category successfully deleted.";
	}

}
