package com.ssafy.farmily.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "스프린트 기록 페이지네이션 DTO")
public class SprintRecordPageResponseDto {
	private List<RecordBriefResponseDto> challenges;
	private List<RecordBriefResponseDto> records;
	private int pageTotal;
}
