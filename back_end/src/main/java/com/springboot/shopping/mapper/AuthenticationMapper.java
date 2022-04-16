package com.springboot.shopping.mapper;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.model.User;
import com.springboot.shopping.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationMapper {

	private final AuthenticationService authenticationService;
	private final CommonMapper commonMapper;

	public String registerUser(RegistrationRequest registrationRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InputFieldException(bindingResult);
		}
		User user = commonMapper.convertToEntity(registrationRequest, User.class);
		return authenticationService.registerUser(user, registrationRequest.getPassword2());
	}

}
