package com.springboot.shopping.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

	private long id;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String name;

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String imgSrc;
}
