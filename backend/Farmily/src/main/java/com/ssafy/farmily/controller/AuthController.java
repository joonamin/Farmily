package com.ssafy.farmily.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@GetMapping("/test")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("test");
	}

}
