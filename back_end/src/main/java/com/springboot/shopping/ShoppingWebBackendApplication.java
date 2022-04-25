package com.springboot.shopping;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
//		return args -> {
//			userService.createRole(new Role(null, "USER"));
//			userService.createRole(new Role(null, "ADMIN"));
//
//			authenticationService.registerUser(
//					new UserEntity(null, "bao", "pham", "baobao", "123456", "0908887777", "Aaaaaaa", new ArrayList<Role>()),
//					"123456");
//
//		};
//	}

}
