package com.ssafy.farmily.dto;

import com.ssafy.farmily.utils.DateRange;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class CalendarPlanResponseDto {
	private String color;
	private String content;
	private DateRange dateRange;
	private Long id;
}
