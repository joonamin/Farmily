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
@Schema(description = "챌린지기록 작성 요청 DTO")
public class ChallengeRecordPostRequestDto {
	private Long sprintId;
	private String title;
	private String content;
	private DateRange dateRange;
}
