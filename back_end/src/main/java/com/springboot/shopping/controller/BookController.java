package com.springboot.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.shopping.dto.book.BookResponse;
import com.springboot.shopping.mapper.BookMapper;

@RestController
@RequestMapping("/api/v1")
public class BookController {

	private final BookMapper bookMapper;

	public BookController(BookMapper bookMapper) {
		super();
		this.bookMapper = bookMapper;
	}

	@GetMapping("/books")
	public ResponseEntity<List<BookResponse>> getAllBooks() {
		return ResponseEntity.ok(bookMapper.findAllBooks());
	}
}
