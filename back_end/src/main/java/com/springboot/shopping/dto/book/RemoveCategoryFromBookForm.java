package com.springboot.shopping.dto.book;

import java.util.List;

import lombok.Data;

@Data
public class RemoveCategoryFromBookForm {
	private long bookId;
	private List<Long> CategoriesId;
}
