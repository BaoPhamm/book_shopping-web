package com.springboot.shopping.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.category.CategoryRequest;
import com.springboot.shopping.dto.category.CategoryResponse;
import com.springboot.shopping.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class CategoryAdminController {

	private final CategoryService categoryService;

	// Create a new category
	@PostMapping("/category/create")
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
		return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
	}

	// Update an existing category
	@PutMapping("/category/update/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long categoryId,
			@RequestBody CategoryRequest categoryRequest) {
		return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryRequest));
	}

	// Delete an existing category by ID
	@DeleteMapping("/category/delete/{id}")
	public ResponseEntity<List<CategoryResponse>> deleteCategory(@PathVariable("id") Long categoryId) {
		return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
	}

}
