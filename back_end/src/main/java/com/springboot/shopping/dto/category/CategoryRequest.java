package com.springboot.shopping.dto.category;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String name;
}
