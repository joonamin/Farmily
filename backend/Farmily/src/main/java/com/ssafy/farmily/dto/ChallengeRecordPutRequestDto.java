package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.type.DateRange;

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
@Schema(description = "챌린지기록 수정 요청 DTO")
public class ChallengeRecordPutRequestDto {
	private Long recordId;
	private String title;
	private String content;
	private DateRange dateRange;
}
