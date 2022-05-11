package com.springboot.shopping.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.rating.RatingRequest;
import com.springboot.shopping.dto.rating.RatingResponse;
import com.springboot.shopping.service.RatingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/rating")
@RequiredArgsConstructor
public class RatingController {

	private final RatingService ratingService;

	@PostMapping()
	public ResponseEntity<RatingResponse> postRating(@Validated @RequestBody RatingRequest ratingRequest) {
		RatingResponse ratingResponse = ratingService.postRating(ratingRequest);
		return ResponseEntity.ok(ratingResponse);
	}

}
