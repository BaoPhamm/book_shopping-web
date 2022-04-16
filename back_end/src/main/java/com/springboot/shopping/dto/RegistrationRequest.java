package com.springboot.shopping.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RegistrationRequest {

	@NotBlank(message = "First name cannot be empty")
	private String firstName;

	@NotBlank(message = "Last name cannot be empty")
	private String lastName;

	@Size(min = 6, max = 16, message = "User name must be between 6 and 16 characters long")
	private String username;

	@Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
	private String password;

	@Size(min = 6, max = 16, message = "The password confirmation must be between 6 and 16 characters long")
	private String password2;

	@Size(min = 10, max = 11, message = "The phone number must be has atleast 10 characters long")
	private String phoneNumber;

	@NotBlank(message = "The address cannot be empty")
	private String address;

}
