package com.springboot.shopping.dto.book;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import com.springboot.shopping.model.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAdminResponse {
	private Long id;
	private String title;
	private String author;
	private Long totalPages;
	private Long requiredAge;
	private LocalDate releaseDate;
	private Integer price;
	private String imgSrc;
	private String description;
	private Double ratingPoint;
	private Long totalRatings;
	private Date createDate;
	private Date updateDate;
	private Set<Category> categories;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookAdminResponse other = (BookAdminResponse) obj;
		return Objects.equals(author, other.author) && Objects.equals(categories, other.categories)
				&& Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(imgSrc, other.imgSrc) && Objects.equals(price, other.price)
				&& Objects.equals(releaseDate, other.releaseDate) && requiredAge == other.requiredAge
				&& Objects.equals(title, other.title) && totalPages == other.totalPages;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, categories, description, id, imgSrc, price, releaseDate, requiredAge, title,
				totalPages);
	}
}
