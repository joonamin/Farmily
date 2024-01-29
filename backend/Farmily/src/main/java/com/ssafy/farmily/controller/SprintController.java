package com.ssafy.farmily.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.RecordBriefResponseDto;
import com.ssafy.farmily.service.sprint.SprintService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sprint")
@RequiredArgsConstructor
@Tag(name = "Sprint", description = "스프린트 API")
public class SprintController {

	private final SprintService sprintService;


	@PostMapping("/{sprintId}/harvest")
	@Operation(
		summary = "스프린트 수확하기",
		description = "특정 스프린트의 열매를 수확합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "수확 성공")
	})
	private ResponseEntity<Void> postHarvest(@PathVariable Long sprintId) {
		sprintService.harvest(sprintId);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/{sprintId}/record")
	@Operation(
		summary = "스프린트 전체 기록 보기",
		description = "특정 스프린트에 속한 모든 기록을 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공")
	})
	private ResponseEntity<List<RecordBriefResponseDto>> postRecord(@PathVariable Long sprintId) {
		List<RecordBriefResponseDto> dtos = sprintService.getRecords(sprintId);

		return ResponseEntity.ok(dtos);
	}
}
