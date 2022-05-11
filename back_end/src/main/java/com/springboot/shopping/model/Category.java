package com.springboot.shopping.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Table(name = "categories", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String imgSrc;

	@ManyToMany()
	@JsonIgnore
	@JoinTable(name = "books_categories", joinColumns = @JoinColumn(name = "categories_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	Set<Book> books;

}
