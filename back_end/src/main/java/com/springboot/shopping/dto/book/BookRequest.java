package com.springboot.shopping.dto.book;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

	private long id;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String title;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String author;

	@NotNull(message = "Fill in the input field")
	private long totalPages;

	@NotNull(message = "Fill in the input field")
	private long requiredAge;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private LocalDate releaseDate;

	@NotNull(message = "Fill in the input field")
	private Integer price;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String description;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String imgSrc;
}
