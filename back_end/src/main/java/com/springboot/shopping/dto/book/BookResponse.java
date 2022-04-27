package com.springboot.shopping.dto.book;

import java.time.LocalDate;
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
public class BookResponse {
	private long id;
	private String title;
	private String author;
	private long totalPages;
	private long requiredAge;
	private LocalDate releaseDate;
	private Integer price;
	private String imgSrc;
	private String description;
	private Set<Category> Categories;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookResponse other = (BookResponse) obj;
		return Objects.equals(author, other.author) && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(imgSrc, other.imgSrc) && Objects.equals(price, other.price)
				&& requiredAge == other.requiredAge && Objects.equals(title, other.title)
				&& totalPages == other.totalPages;
	}
	@Override
	public int hashCode() {
		return Objects.hash(author, description, id, imgSrc, price, requiredAge, title, totalPages);
	}
}
