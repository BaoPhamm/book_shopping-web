package com.springboot.shopping.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
	private Double ratingPoint;
	private long totalRatings;
	private Date createDate;
	private Date updateDate;

	@PostPersist
	protected void onCreate() {
		createDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updateDate = new Date();
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "books_categories", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "categories_id"))
	private Set<Category> categories = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
	private Set<BookRating> bookRatings = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && Objects.equals(createDate, other.createDate)
				&& Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(imgSrc, other.imgSrc) && Objects.equals(price, other.price)
				&& Objects.equals(ratingPoint, other.ratingPoint) && Objects.equals(releaseDate, other.releaseDate)
				&& requiredAge == other.requiredAge && Objects.equals(title, other.title)
				&& totalPages == other.totalPages && totalRatings == other.totalRatings
				&& Objects.equals(updateDate, other.updateDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, createDate, description, id, imgSrc, price, ratingPoint, releaseDate, requiredAge,
				title, totalPages, totalRatings, updateDate);
	}

}
