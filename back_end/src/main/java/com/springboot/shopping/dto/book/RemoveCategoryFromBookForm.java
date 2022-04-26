package com.springboot.shopping.dto.book;

import lombok.Data;

@Data
public class RemoveCategoryFromBookForm {
	private String bookTitle;
	private String categoryName;
}
