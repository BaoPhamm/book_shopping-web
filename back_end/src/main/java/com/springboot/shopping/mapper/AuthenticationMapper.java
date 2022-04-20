package com.springboot.shopping.mapper;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.dto.auth.AuthenticationRequest;
import com.springboot.shopping.dto.auth.AuthenticationResponse;
import com.springboot.shopping.exception.InputFieldException;
import com.springboot.shopping.model.UserEntity;
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
		UserEntity user = commonMapper.convertToEntity(registrationRequest, UserEntity.class);
		return authenticationService.registerUser(user, registrationRequest.getPassword2());
	}

	public AuthenticationResponse login(AuthenticationRequest request) {
		Map<String, String> credentials = authenticationService.login(request.getUsername(), request.getPassword());
		AuthenticationResponse response = new AuthenticationResponse();
		response.setUsername(credentials.get("username"));
		response.setToken(credentials.get("accessToken"));
		response.setRefreshToken(credentials.get("refreshToken"));
		response.setUserRoles(credentials.get("userRoles"));
		return response;
	}

	public String passwordReset(String username, PasswordResetRequest passwordReset) {
		return authenticationService.passwordReset(username, passwordReset.getPassword(), passwordReset.getPassword2());
	}

}
