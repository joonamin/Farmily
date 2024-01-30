package com.ssafy.farmily.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedHeaders("*")
			.allowedOrigins("http://localhost:5173", "http://i10e102.p.ssafy.io:5173", "http://testCors.kr")
			.allowCredentials(true)
			.allowedMethods("*");
	}
}
