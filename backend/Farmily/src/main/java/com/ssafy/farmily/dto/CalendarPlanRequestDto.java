package com.ssafy.farmily.dto;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.annotation.NotInverted;
import com.ssafy.farmily.validation.annotation.StartsNowOrLater;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CalendarPlanRequestDto {
	@NotInverted
	@StartsNowOrLater
	private DateRange dateRange;
	private Long familyId;
	private String content;
	private String color;
}
