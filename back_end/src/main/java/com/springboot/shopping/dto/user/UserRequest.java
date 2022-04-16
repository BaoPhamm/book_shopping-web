package com.springboot.shopping.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	@NotBlank(message = "First name cannot be empty")
	private String firstName;

	@NotBlank(message = "Last name cannot be empty")
	private String lastName;

	@Size(min = 6, max = 16, message = "User name must be between 6 and 16 characters long")
	private String username;

	@Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
	private String password;

	@Size(min = 10, max = 11, message = "The phone number must be has atleast 10 characters long")
	private String phoneNumber;

	@NotBlank(message = "The address cannot be empty")
	private String address;

}
