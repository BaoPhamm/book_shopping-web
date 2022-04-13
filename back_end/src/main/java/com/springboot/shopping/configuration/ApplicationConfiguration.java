package com.springboot.shopping.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

	@Value("${cors.allowedOrigins}")
	private String allowedOrigins;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
