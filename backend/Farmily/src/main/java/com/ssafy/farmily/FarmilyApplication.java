package com.ssafy.farmily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableAspectJAutoProxy
@SpringBootApplication
public class FarmilyApplication {
	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(FarmilyApplication.class, args);

	}
}
