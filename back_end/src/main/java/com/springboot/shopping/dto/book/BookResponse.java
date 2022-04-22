package com.springboot.shopping.dto.book;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
	private long id;
	private String title;
	private String author;
	private long totalPages;
	private long requiredAge;
	private Date releaseDate;
	private Integer price;
	private String imgSrc;
	private String description;
}
