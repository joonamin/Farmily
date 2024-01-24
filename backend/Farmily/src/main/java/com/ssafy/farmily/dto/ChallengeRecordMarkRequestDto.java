package com.ssafy.farmily.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "챌린지기록 표시 요청 DTO")
public class ChallengeRecordMarkRequestDto {
	private Long challengeId;
	private LocalDate date;
}
