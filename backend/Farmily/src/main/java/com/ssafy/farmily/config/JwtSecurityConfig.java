// package com.ssafy.farmily.config;
//
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.DefaultSecurityFilterChain;
// import org.springframework.security.web.authentication.logout.LogoutFilter;
//
// // import com.ssafy.farmily.filter.JwtAuthenticationFilter;
//
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
// 	private final AuthenticationManager authenticationManager;
// 	@Override
// 	public void configure(HttpSecurity http) throws Exception {
// 		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager);
// 		http.addFilterBefore(filter, LogoutFilter.class);
// 	}
//
// }
