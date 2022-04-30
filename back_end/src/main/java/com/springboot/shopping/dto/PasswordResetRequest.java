package com.springboot.shopping.dto;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PasswordResetRequest {

	@Size(min = 4, max = 16, message = "The password must be between 4 and 16 characters long")
	private String currentPassword;

	@Size(min = 4, max = 16, message = "The password must be between 4 and 16 characters long")
	private String newPassword;

	@Size(min = 4, max = 16, message = "The password confirmation must be between 4 and 16 characters long")
	private String newPasswordRepeat;
}
