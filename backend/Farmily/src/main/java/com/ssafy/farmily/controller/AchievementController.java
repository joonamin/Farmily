package com.ssafy.farmily.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.AchievementRewardRequestDto;
import com.ssafy.farmily.dto.AchievementRewardResponseDto;
import com.ssafy.farmily.dto.FamilyStatisticsResponseDto;
import com.ssafy.farmily.service.achievement.AchievementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievement")
public class AchievementController {
	private final AchievementService achievementService;

	@GetMapping("/{familyId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Operation(
		summary = "업적 가져오기",
		description = "가족 ID를 통해 해당 가족의 업적 현황을 불러옵니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "업적 불러오기 성공"
		)
	})
	public ResponseEntity<List<FamilyStatisticsResponseDto>> getAchievements(@PathVariable Long familyId) {
		List<FamilyStatisticsResponseDto> achievementProgress = achievementService.familyAchievementProgress(familyId);
		return ResponseEntity.ok(achievementProgress);
	}

	@PostMapping("/getReward")
	public ResponseEntity<AchievementRewardResponseDto> receiveReward(
		@Valid @RequestBody AchievementRewardRequestDto dto,
		@AuthenticationPrincipal String username) {
		AchievementRewardResponseDto responseDto = achievementService.receiveReward(dto, username);
		return ResponseEntity.ok(responseDto);
	}
}
