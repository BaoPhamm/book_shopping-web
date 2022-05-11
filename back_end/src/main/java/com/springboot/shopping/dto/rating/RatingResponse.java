package com.springboot.shopping.dto.rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingResponse {
	private Long id;
	private Long bookId;
	private Long userId;
	private Long point;
	private String comment;
}
