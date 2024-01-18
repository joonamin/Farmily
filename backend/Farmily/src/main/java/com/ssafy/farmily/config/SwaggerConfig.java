package com.ssafy.farmily.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(apiInfo());
	}
	private Info apiInfo() {
		return new Info()
			.title("spring doc 테스트")
			.description("spring doc을 이용한 swagger ui 테스트")
			.version("1.0.0");
	}
}
