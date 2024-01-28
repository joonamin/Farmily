package com.ssafy.farmily.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import lombok.RequiredArgsConstructor;

// import com.ssafy.farmily.filter.JwtAuthenticationFilter;
import com.ssafy.farmily.filter.JwtAuthenticationFilter;
import com.ssafy.farmily.utils.CustomAuthenticationSuccessHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final OidcUserService oidcUserService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
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
			// 여기에 authorize가 필요한 method에 대해 적용되는 filter를 customizing 해야함
			.httpBasic(HttpBasicConfigurer::disable);

		return http.build();
	}

}
