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

	@NotBlank(message = "Please fill in the title field")
	@Length(max = 255)
	private String title;

	@NotBlank(message = "Please fill in the author field")
	@Length(max = 255)
	private String author;

	@NotNull(message = "Please fill in the total pages field")
	private long totalPages;

	@NotNull(message = "Please fill in the required age field")
	private long requiredAge;

	@NotNull(message = "Please fill in the release date field")
	private LocalDate releaseDate;

	@NotNull(message = "Please fill in the price field")
	private Integer price;

	@NotBlank(message = "Please fill in the description field")
	@Length(max = 510)
	private String description;

	@NotBlank(message = "Please fill in the image URL field")
	@Length(max = 255)
	private String imgSrc;
}
