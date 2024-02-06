package com.ssafy.farmily.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.SprintRecordFirstResponseDto;
import com.ssafy.farmily.dto.SprintRecordPageResponseDto;
import com.ssafy.farmily.service.sprint.SprintService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
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
		summary = "스프린트 기록 최초 조회",
		description = "특정 스프린트에 속한 기록을 최초로 조회할 때 호출되어야 합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공")
	})
	private ResponseEntity<SprintRecordFirstResponseDto> getRecordInitially(
		@PathVariable Long sprintId,
		@RequestParam @Min(1) int pageSize,
		@RequestParam(defaultValue = "10") @Min(0) int imageCount
	) {
		SprintRecordFirstResponseDto dto = sprintService.getRecordsInitially(sprintId, pageSize, imageCount);

		return ResponseEntity.ok(dto);
	}

	@GetMapping("/{sprintId}/record/{pageNo}")
	@Operation(
		summary = "스프린트 기록 페이지 조회",
		description = "특정 스프린트에 속한 기록의 페이지 정보를 가져옵니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공")
	})
	private ResponseEntity<SprintRecordPageResponseDto> getRecordPagination(
		@PathVariable Long sprintId,
		@PathVariable @Min(1) int pageNo,
		@RequestParam @Min(1) int pageSize
	) {
		SprintRecordPageResponseDto dto = sprintService.getRecordsPagination(sprintId, pageNo, pageSize);

		return ResponseEntity.ok(dto);
	}
}
