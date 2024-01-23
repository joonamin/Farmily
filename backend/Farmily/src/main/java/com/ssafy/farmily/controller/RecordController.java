package com.ssafy.farmily.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.service.record.RecordService;

import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/record")
@Tag(name = "Record", description = "기록 API")
public class RecordController {
	@Autowired
	RecordService responseService;

	@GetMapping("/{requestId}")
	@Operation(
		summary = "기록 조회",
		description = "특정 기록의 모든 내용을 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "기록 조회 성공",
			content = @Content(schema = @Schema(implementation = RecordResponseDto.class))
		)
	})
	private ResponseEntity<RecordResponseDto> get(
		@Parameter(description = "요청할 기록 ID") @PathVariable Long requestId
	) {
		RecordResponseDto dto = responseService.getById(requestId);

		return ResponseEntity.ok(dto);
	}


	@PostMapping("/daily")
	@Operation(
		summary = "일상 기록 작성",
		description = "일상 기록을 작성합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "기록 작성 성공")
	})
	private ResponseEntity<Void> postDaily(
		// @AuthenticationPrincipal UserDetails userDetails,
		@RequestBody DailyRecordPostRequestDto request
	) {
		responseService.createDaily(request);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/daily")
	@Operation(
		summary = "일상 기록 수정",
		description = "일상 기록을 수정합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "기록 수정 성공")
	})
	private ResponseEntity<Void> putDaily(
		// TODO: userdetails 추가
		@RequestBody DailyRecordPutRequestDto request
	) {
		responseService.editDaily(request);

		return ResponseEntity.ok().build();
	}
}
