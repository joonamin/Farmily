package com.ssafy.farmily.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "챌린지기록 수정 요청 DTO")
public class ChallengeRecordPutRequestDto {
	@NotNull
	private Long recordId;

	@NotBlank
	private String title;

	@NotBlank
	private String content;
}
