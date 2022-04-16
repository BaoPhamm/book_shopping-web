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
	private long total_pages;
	private long required_age;
	private Date release_date;
	private Double price;
	private String img_src;
}
