package com.ssafy.farmily.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.farmily.filter.ExceptionHandlerFilter;
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
	private final ExceptionHandlerFilter exceptionHandlerFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(sm -> {
				sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				sm.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::none);
			})
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
			.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
			.httpBasic(HttpBasicConfigurer::disable)
		;

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://i10e102.p.ssafy.io:5173", "https://i10e102.p.ssafy.io:5173", "http://localhost:5173"));
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
