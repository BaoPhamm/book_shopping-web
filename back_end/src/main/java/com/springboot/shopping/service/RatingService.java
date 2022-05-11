package com.springboot.shopping.service;

import com.springboot.shopping.dto.rating.RatingRequest;
import com.springboot.shopping.dto.rating.RatingResponse;

public interface RatingService {

	RatingResponse postRating(RatingRequest ratingRequest);

}
