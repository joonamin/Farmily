package com.ssafy.farmily.dto;

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
public class DailyRecordRequestDto {
	private Long sprintId;
	private String title;
	private String content;
}
