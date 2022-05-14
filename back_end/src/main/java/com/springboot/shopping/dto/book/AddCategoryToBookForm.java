package com.springboot.shopping.dto.book;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class AddCategoryToBookForm {
	private long bookId;
	private List<Long> categoriesId;
}
