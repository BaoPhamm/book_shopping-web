package com.springboot.shopping.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.category.CategoryResponse;
import com.springboot.shopping.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	// Get category by ID
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long categoryId) {
		CategoryResponse bookResponse = categoryService.findCategoryById(categoryId);
		return ResponseEntity.ok(bookResponse);
	}

	// Get category by ID
	@GetMapping("/{name}")
	public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable("name") String categoryName) {
		CategoryResponse bookResponse = categoryService.findCategoryByName(categoryName);
		return ResponseEntity.ok(bookResponse);
	}

	// Get all categories
	@GetMapping()
	public ResponseEntity<List<CategoryResponse>> getAllCategories() {
		List<CategoryResponse> allCategories = categoryService.findAllCategories();
		return ResponseEntity.ok(allCategories);
	}

}
