package com.ssafy.farmily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FarmilyApplication {
	public static void main(String[] args) {
		SpringApplication.run(FarmilyApplication.class, args);
	}

}