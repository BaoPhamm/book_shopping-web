package com.springboot.shopping.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
	@Bean
	@ConditionalOnMissingBean(OpenAPI.class)
	public OpenAPI kmsConnectOpenAPI() {
		var apiSecretToken = new SecurityScheme().name("bearerAuth").type(SecurityScheme.Type.HTTP).scheme("bearer")
				.description("Set header with the secret in your app configuration to authenticate.")
				.bearerFormat("JWT");
		return new OpenAPI()
				.info(new Info().title("Book shopping web API").description(
						"<p><em>Book shopping web API</em></p>").version("v1.0.0")
						.license(new License().name("Copyright ©2022 BaoPham")
								.url("https://www.github.com/BaoPhamm")))
				.components(new Components().addSecuritySchemes("appSecret", apiSecretToken))
				.addSecurityItem(new SecurityRequirement().addList("appSecret"));
	}

	@Bean
	public GroupedOpenApi scheduleApi() {
		return GroupedOpenApi.builder().group("Book shopping web API").pathsToMatch("/**").build();
	}
}