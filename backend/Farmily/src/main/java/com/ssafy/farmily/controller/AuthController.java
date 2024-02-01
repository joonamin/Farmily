package com.ssafy.farmily.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.LogoutRequestDto;
import com.ssafy.farmily.service.member.LogoutResponseDto;
import com.ssafy.farmily.service.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

	public final MemberService memberService;
	@GetMapping("/test")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<String> testGetUserInfo(@AuthenticationPrincipal String username) {
		return ResponseEntity.ok("username: " + username);
	}

	@PostMapping("/token/expire")
	public ResponseEntity<LogoutResponseDto> logout(@AuthenticationPrincipal String username, @RequestBody LogoutRequestDto logoutRequestDto) {
		// todo: username과 logoutRequestDto의 access-token 'sub' claim이 일치하는지 검증
		// *. 직접 db 조회
		// 또는. SecurityContext에 저장된 Principal 정보를 {username, accessToken} 으로 수정
		return ResponseEntity.ok(memberService.logout(logoutRequestDto));
	}
}
