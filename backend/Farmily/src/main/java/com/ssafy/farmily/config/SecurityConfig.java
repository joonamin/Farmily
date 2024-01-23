package com.ssafy.farmily.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private OidcUserService oidcUserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(CorsConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			// 추후에 인증이 필요한 url을 등록합니다.
			.authorizeHttpRequests(req ->
				req.requestMatchers("/**")
					.permitAll()
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			)
			.oauth2Login(
				oauth2 -> oauth2
					.userInfoEndpoint(userInfo -> userInfo
						.oidcUserService(oidcUserService))

			)
			.httpBasic(HttpBasicConfigurer::disable);
		return http.build();
	}

}
