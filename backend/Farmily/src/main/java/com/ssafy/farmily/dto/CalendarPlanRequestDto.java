package com.ssafy.farmily.dto;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.annotation.NotInverted;
import com.ssafy.farmily.validation.annotation.StartsNowOrLater;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CalendarPlanRequestDto {
	@NotInverted
	private DateRange dateRange;
	@NotNull
	private Long familyId;

	@NotBlank
	private String content;

	@NotNull
	@Pattern(regexp = "^[0-9A-Fa-f]{6}$")
	private String color;
}
