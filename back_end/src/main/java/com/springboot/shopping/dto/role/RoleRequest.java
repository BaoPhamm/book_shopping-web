package com.springboot.shopping.dto.role;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {

	@NotBlank(message = "Role name cannot be empty")
	private String name;
}