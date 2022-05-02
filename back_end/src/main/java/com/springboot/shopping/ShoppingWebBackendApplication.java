package com.springboot.shopping;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.BindingResult;

import com.springboot.shopping.dto.RegistrationRequest;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;
import com.springboot.shopping.service.AuthenticationService;
import com.springboot.shopping.service.UserService;

@SpringBootApplication
public class ShoppingWebBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingWebBackendApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(UserService userService, AuthenticationService authenticationService) {
//		return args -> authenticationService.registerUser(
//				new RegistrationRequest("bao", "pham", "baobaoo", "123456", "123456", "0908887778", "BBbbbbb") );
//	}

}
