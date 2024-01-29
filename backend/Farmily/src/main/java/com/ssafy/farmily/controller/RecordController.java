package com.ssafy.farmily.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.ChallengeRecordMarkRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPostRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPutRequestDto;
import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordPostRequestDto;
import com.ssafy.farmily.dto.EventRecordPutRequestDto;
import com.ssafy.farmily.dto.RecordResponseDto;
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
	private ResponseEntity<RecordResponseDto> get(
		@Parameter(description = "요청할 기록 ID") @PathVariable Long requestId
	) {
		RecordResponseDto dto = recordService.getEventDtoById(requestId);

		return ResponseEntity.ok(dto);
	}

	@GetMapping("/{requestId}/event")
	@Operation(
		summary = "이벤트 기록 조회",
		description = "특정 이벤트 기록의 모든 내용을 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "기록 조회 성공",
			content = @Content(schema = @Schema(implementation = RecordResponseDto.class))
		)
	})
	@Deprecated
	private ResponseEntity<RecordResponseDto> getEvent(
		@Parameter(description = "요청할 기록 ID") @PathVariable Long requestId
	) {
		RecordResponseDto dto = recordService.getEventDtoById(requestId);

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
	@Deprecated
	private ResponseEntity<Void> postEvent(
		// TODO: userdetails 추가
		@Valid EventRecordPostRequestDto request
	) {
		recordService.createEventRecord(request);

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
		// TODO: userdetails 추가
		@Valid EventRecordPutRequestDto request
	) {
		recordService.editEventRecord(request);

		return ResponseEntity.ok().build();
	}


	@GetMapping("/{requestId}/daily")
	@Operation(
		summary = "일상 기록 조회",
		description = "특정 일상 기록의 모든 내용을 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "기록 조회 성공",
			content = @Content(schema = @Schema(implementation = RecordResponseDto.class))
		)
	})
	@Deprecated
	private ResponseEntity<RecordResponseDto> getDaily(
		@Parameter(description = "요청할 기록 ID") @PathVariable Long requestId
	) {
		RecordResponseDto dto = recordService.getDtoById(requestId);

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
		// TODO: userdetails 추가
		@Valid @RequestBody DailyRecordPostRequestDto request
	) {
		recordService.createDailyRecord(request);

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
		@Valid @RequestBody DailyRecordPutRequestDto request
	) {
		recordService.editDailyRecord(request);

		return ResponseEntity.ok().build();
	}


	@GetMapping("/{requestId}/challenge")
	@Operation(
		summary = "챌린지 기록 조회",
		description = "특정 챌린지 기록의 모든 내용을 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "기록 조회 성공",
			content = @Content(schema = @Schema(implementation = RecordResponseDto.class))
		)
	})
	private ResponseEntity<RecordResponseDto> getChallenge(
		@Parameter(description = "요청할 기록 ID") @PathVariable Long requestId
	) {
		RecordResponseDto dto = recordService.getChallengeDtoById(requestId);

		return ResponseEntity.ok(dto);
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
		// TODO: userdetails 추가
		@Valid @RequestBody ChallengeRecordPostRequestDto request
	) {
		recordService.createChallengeRecord(request);

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
		// TODO: userdetails 추가
		@Valid @RequestBody ChallengeRecordMarkRequestDto request
	) {
		recordService.markChallengeRecord(request);

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
		// TODO: userdetails 추가
		@Valid @RequestBody ChallengeRecordPutRequestDto request
	) {
		recordService.editChallengeRecord(request);

		return ResponseEntity.ok().build();
	}
}
