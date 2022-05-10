package com.springboot.shopping.dto.role;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RoleRequest {

	@NotBlank(message = "Role name cannot be empty")
	private String name;
}