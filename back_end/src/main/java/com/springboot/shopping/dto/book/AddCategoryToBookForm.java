package com.springboot.shopping.dto.book;

import lombok.Data;

@Data
public class AddCategoryToBookForm {
	private String bookTitle;
	private String categoryName;
}
