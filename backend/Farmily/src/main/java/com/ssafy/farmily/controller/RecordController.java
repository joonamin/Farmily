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

@RestController
@RequestMapping("/record")
public class RecordController {
	@Autowired
	RecordService responseService;

	@GetMapping("/{requestId}")
	private ResponseEntity<RecordResponseDto> get(@PathVariable Long requestId) {
		RecordResponseDto dto = responseService.getById(requestId);

		return ResponseEntity.ok(dto);
	}

	@PostMapping("/daily")
	private ResponseEntity<Void> postDaily(
		// @AuthenticationPrincipal UserDetails userDetails,
		@RequestBody DailyRecordPostRequestDto request
	) {
		responseService.createDaily(request);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/daily")
	private ResponseEntity<Void> putDaily(
		// TODO: userdetails 추가
		@RequestBody DailyRecordPutRequestDto request
	) {
		responseService.editDaily(request);

		return ResponseEntity.ok().build();
	}
}
