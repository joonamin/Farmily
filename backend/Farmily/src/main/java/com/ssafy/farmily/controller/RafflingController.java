package com.ssafy.farmily.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.RafflingRequestDto;
import com.ssafy.farmily.dto.RafflingResponseDto;
import com.ssafy.farmily.service.raffling.RafflingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/raffling")
public class RafflingController {
	private final RafflingService rafflingService;

	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(
		summary = "뽑기",
		description = "가족 ID를 request로 남은 point와 뽑은 item을 return합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "뽑기 성공"
		)
	})
	public ResponseEntity<RafflingResponseDto> raffleItem(@RequestBody RafflingRequestDto dto,
		@AuthenticationPrincipal String username) {
		RafflingResponseDto responseDto = rafflingService.raffleItem(dto, username);
		return ResponseEntity.ok(responseDto);
	}
}
