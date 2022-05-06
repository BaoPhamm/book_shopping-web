package com.springboot.shopping.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Table(name = "books", uniqueConstraints = { @UniqueConstraint(columnNames = "title") })
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String title;
	private String author;
	private long totalPages;
	private long requiredAge;
	private LocalDate releaseDate;
	private Integer price;
	private String imgSrc;
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Category> categories = new ArrayList<>();

//	@OneToMany(fetch = FetchType.EAGER)
//	private Collection<BookRating> bookRatings = new ArrayList<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
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
