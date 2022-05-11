package com.springboot.shopping.dto.rating;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
	private Long id;

	@NotNull(message = "Book Id is null")
	private Long bookId;

	@NotNull(message = "User Id is null")
	private Long userId;

	@NotNull(message = "Please rate this book.")
	private Long point;

	@NotBlank(message = "Please fill in the comment field.")
	@Length(max = 255)
	private String comment;
}
