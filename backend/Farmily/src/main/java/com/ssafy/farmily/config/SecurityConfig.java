package com.ssafy.farmily.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.ssafy.farmily.filter.JwtAuthenticationFilter;
import com.ssafy.farmily.utils.CustomAuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final OidcUserService oidcUserService;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(CorsConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			// 추후에 인증이 필요한 url을 등록합니다.
			.authorizeHttpRequests(req ->
				req.requestMatchers("/**").permitAll()
					// .requestMatchers("/auth/test").hasRole("USER")
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.anyRequest().authenticated()
			)
			.oauth2Login(
				oauth2 -> oauth2
					.userInfoEndpoint(userInfo -> userInfo
						.oidcUserService(oidcUserService))
					.successHandler(customAuthenticationSuccessHandler)
			)
			// 로그아웃 필터가 현재 가장 먼저 실행되는 filter입니다.
			.addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class)
			.httpBasic(HttpBasicConfigurer::disable);

		return http.build();
	}

}
