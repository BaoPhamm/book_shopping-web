package com.springboot.shopping.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	@Value("${cors.allowedOrigins}")
	private String allowedOrigins;

//	@Bean
//	public RestTemplate getRestTemplate() {
//		return new RestTemplate();
//	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/v1/**").allowedOrigins("*")
				.allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE").allowedHeaders("*");
	}
}
