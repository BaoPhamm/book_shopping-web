package com.springboot.shopping.dto.book;

import java.sql.Date;

public class BookResponse {
	private long id;
	private String title;
	private String author;
	private long total_pages;
	private long required_age;
	private Date release_date;
	private Double price;
	private String img_src;

	public BookResponse() {
		super();
	}

	public BookResponse(long id, String title, String author, long total_pages, long required_age, Date release_date,
			Double price, String img_src) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.total_pages = total_pages;
		this.required_age = required_age;
		this.release_date = release_date;
		this.price = price;
		this.img_src = img_src;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(long total_pages) {
		this.total_pages = total_pages;
	}

	public long getRequired_age() {
		return required_age;
	}

	public void setRequired_age(long required_age) {
		this.required_age = required_age;
	}

	public Date getRelease_date() {
		return release_date;
	}

	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImg_src() {
		return img_src;
	}

	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}

}
