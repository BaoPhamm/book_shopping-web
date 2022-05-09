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

	@NotBlank(message = "title blank")
	@Length(max = 255)
	private String title;

	@NotBlank(message = "author blank")
	@Length(max = 255)
	private String author;

	@NotNull(message = "totalPages blank")
	private long totalPages;

	@NotNull(message = "requiredAge blank")
	private long requiredAge;

	@NotNull(message = "releaseDate blank")
	private LocalDate releaseDate;

	@NotNull(message = "price blank")
	private Integer price;

	@NotBlank(message = "description blank")
	@Length(max = 510)
	private String description;

	@NotBlank(message = "imgSrc blank")
	@Length(max = 255)
	private String imgSrc;
}
