package com.springboot.shopping.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.shopping.dto.rating.RatingRequest;
import com.springboot.shopping.dto.rating.RatingResponse;
import com.springboot.shopping.exception.rating.RatingExistException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.BookRating;
import com.springboot.shopping.repository.RatingRepository;
import com.springboot.shopping.service.RatingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

	private final CommonMapper commonMapper;
	private final RatingRepository ratingRepository;

	@Override
	public RatingResponse postRating(RatingRequest ratingRequest) {
		Optional<BookRating> existBookRating = ratingRepository.findExistUserBookRating(ratingRequest.getUserId(),
				ratingRequest.getBookId());
		if (existBookRating.isPresent()) {
			throw new RatingExistException();
		}
		BookRating newBookRating = commonMapper.convertToEntity(ratingRequest, BookRating.class);
		BookRating savedBookRating = ratingRepository.save(newBookRating);
		System.out.println(savedBookRating.getComment());
		System.out.println(savedBookRating.getId());
		System.out.println(savedBookRating.getPoint());
		return commonMapper.convertToResponse(savedBookRating, RatingResponse.class);
	}

}
