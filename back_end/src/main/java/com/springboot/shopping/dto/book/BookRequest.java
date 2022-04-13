package com.springboot.shopping.dto.book;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class BookRequest {

	private long id;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String title;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String author;

	@NotNull(message = "Fill in the input field")
	private long total_pages;

	@NotNull(message = "Fill in the input field")
	private long required_age;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private Date release_date;

	@NotNull(message = "Fill in the input field")
	private Double price;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String img_src;

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
