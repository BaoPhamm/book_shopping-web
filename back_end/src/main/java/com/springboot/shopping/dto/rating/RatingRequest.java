package com.springboot.shopping.dto.rating;

import javax.validation.constraints.NotNull;

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
}
