package com.ssafy.farmily.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.ChallengeRecordMarkRequestDto;


import com.ssafy.farmily.dto.ChallengeRecordPostRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPutRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordResponseDto;
import com.ssafy.farmily.dto.ChallengeRewardRequestDto;
import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordPostRequestDto;
import com.ssafy.farmily.dto.EventRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordResponseDto;
import com.ssafy.farmily.dto.RecordCommentDto;
import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.dto.ServiceProcessResult;
import com.ssafy.farmily.service.record.RecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
@Tag(name = "Record", description = "기록 API")
public class RecordController {

	private final RecordService recordService;

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
	private ResponseEntity<? extends RecordResponseDto> get(
		@Parameter(description = "요청할 기록 ID") @PathVariable Long requestId
	) {
		RecordResponseDto dto = recordService.getDtoById(requestId);
		if (dto instanceof ChallengeRecordResponseDto)
			return ResponseEntity.ok((ChallengeRecordResponseDto) dto);
		else if (dto instanceof EventRecordResponseDto)
			return ResponseEntity.ok((EventRecordResponseDto) dto);
		else
			return ResponseEntity.ok(dto);
	}

	@PostMapping("/event")
	@Operation(
		summary = "이벤트 기록 작성",
		description = "이벤트 기록을 작성합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "기록 작성 성공")
	})
	private ResponseEntity<Void> postEvent(
		@AuthenticationPrincipal String username,
		@Valid EventRecordPostRequestDto request
	) {
		recordService.createEventRecord(username, request);

		return ResponseEntity.ok().build();
	}


	@PutMapping("/event")
	@Operation(
		summary = "이벤트 기록 수정",
		description = "이벤트 기록을 수성합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "기록 수정 성공")
	})
	private ResponseEntity<Void> putEvent(
		@AuthenticationPrincipal String username,
		@Valid EventRecordPutRequestDto request
	) {
		recordService.editEventRecord(username, request);

		return ResponseEntity.ok().build();
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
		@AuthenticationPrincipal String username,
		@Valid @RequestBody DailyRecordPostRequestDto request
	) {
		recordService.createDailyRecord(username, request);

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
		@AuthenticationPrincipal String username,
		@Valid @RequestBody DailyRecordPutRequestDto request
	) {
		recordService.editDailyRecord(username, request);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/challenge")
	@Operation(
		summary = "챌린지 기록 작성",
		description = "챌린지 기록을 작성합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "기록 작성 성공")
	})
	private ResponseEntity<Void> postChallenge(
		@AuthenticationPrincipal String username,
		@Valid @RequestBody ChallengeRecordPostRequestDto request
	) {
		recordService.createChallengeRecord(username, request);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/challenge/mark")
	@Operation(
		summary = "챌린지 기록 표시",
		description = "챌린지 기록의 특정 날짜에 표시합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "표시 성공")
	})
	private ResponseEntity<Void> postChallengeMark(
		@AuthenticationPrincipal String username,
		@Valid @RequestBody ChallengeRecordMarkRequestDto request
	) {
		recordService.markChallengeRecord(username, request);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/challenge")
	@Operation(
		summary = "챌린지 기록 수정",
		description = "챌린지 기록을 수정합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "기록 수정 성공")
	})
	private ResponseEntity<Void> putChallenge(
		@AuthenticationPrincipal String username,
		@Valid @RequestBody ChallengeRecordPutRequestDto request
	) {
		recordService.editChallengeRecord(username, request);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/{recordId}/comment")
	@Operation(
		summary = "기록 댓글 작성",
		description = "기록에 댓글을 작성합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "댓글 작성 성공")
	})
	private ResponseEntity<Void> postComment(
		@PathVariable Long recordId,
		@AuthenticationPrincipal String username,
		@RequestBody RecordCommentDto.Request.Post dto
	) {
		recordService.createComment(recordId, username, dto);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/{recordId}/comment/{commentId}")
	@Operation(
		summary = "기록 댓글 수정",
		description = "기록 댓글을 수정합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "댓글 수정 성공")
	})
	private ResponseEntity<Void> putComment(
		@PathVariable Long recordId,
		@PathVariable Long commentId,
		@AuthenticationPrincipal String username,
		@RequestBody RecordCommentDto.Request.Put dto
	) {
		recordService.editComment(recordId, commentId, username, dto);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/{recordId}/receive-reward")
	private ResponseEntity<Void> receiveChallengeReward(
		@AuthenticationPrincipal String username,
		@PathVariable Long recordId,
		@RequestBody ChallengeRewardRequestDto dto
	){
		recordService.setChallengeComplete(username,recordId,dto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{recordId}")
	private ResponseEntity<Void> deleteRecord(
		@AuthenticationPrincipal String username,
		@PathVariable(value = "recordId") Long recordId) {
			recordService.deleteRecord(username,recordId);
			return ResponseEntity.ok().build();
	}
}
