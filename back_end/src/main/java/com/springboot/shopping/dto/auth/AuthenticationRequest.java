package com.springboot.shopping.dto.auth;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AuthenticationRequest {

	@NotBlank(message = "Username cannot be empty")
	private String username;
	@NotBlank(message = "Password cannot be empty")
	private String password;

}
