package com.springboot.shopping.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.shopping.dto.rating.RatingRequest;
import com.springboot.shopping.dto.rating.RatingResponse;
import com.springboot.shopping.exception.book.BookNotFoundException;
import com.springboot.shopping.exception.rating.RatingExistException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Book;
import com.springboot.shopping.model.BookRating;
import com.springboot.shopping.repository.BookRepository;
import com.springboot.shopping.repository.RatingRepository;
import com.springboot.shopping.service.RatingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

	private final CommonMapper commonMapper;
	private final RatingRepository ratingRepository;
	private final BookRepository bookRepository;

	@Override
	public RatingResponse postRating(RatingRequest ratingRequest) {
		Optional<BookRating> existBookRating = ratingRepository.findExistUserBookRating(ratingRequest.getUserId(),
				ratingRequest.getBookId());
		if (existBookRating.isPresent()) {
			throw new RatingExistException();
		}
		BookRating newBookRating = commonMapper.convertToEntity(ratingRequest, BookRating.class);
		BookRating savedBookRating = ratingRepository.save(newBookRating);

		Long totalBookRatings = bookRepository.getTotalRatings(ratingRequest.getBookId());
		Book currentBook = bookRepository.findById(ratingRequest.getBookId()).orElseThrow(() -> new BookNotFoundException());

		Double newRatingPoint = 1.0
				* ((currentBook.getRatingPoint() * (totalBookRatings - 1) + ratingRequest.getPoint())
						/ totalBookRatings);
		currentBook.setRatingPoint(newRatingPoint);
		currentBook.setTotalRatings(totalBookRatings);
		bookRepository.save(currentBook);
		return commonMapper.convertToResponse(savedBookRating, RatingResponse.class);
	}

	@Override
	public Long getUserRatingPoingBook(String username, Long bookId) {
		bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
		Long point = ratingRepository.getUserRatingPoingBook(username, bookId);
		if (point == null) {
			return (long) 0;
		} else {
			return point;
		}
	}

}
