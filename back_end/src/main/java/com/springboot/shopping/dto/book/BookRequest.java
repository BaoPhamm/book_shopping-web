package com.springboot.shopping.dto.book;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String title;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String author;

	@NotNull(message = "Fill in the input field")
	private long total_pages;

	@NotNull(message = "Fill in the input field")
	private long required_age;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private Date release_date;

	@NotNull(message = "Fill in the input field")
	private Double price;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String img_src;
}
