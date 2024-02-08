package com.ssafy.farmily.dto;

import java.util.List;

import com.ssafy.farmily.utils.DateRange;

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
@Schema(description = "스프린트 기록 최초 요청 DTO")
public class SprintRecordFirstResponseDto {
	private SprintRecordCountsDto counts;
	private DateRange dateRange;
	private List<ImageCardImageDto> images;
	private SprintRecordPageResponseDto page;
}
