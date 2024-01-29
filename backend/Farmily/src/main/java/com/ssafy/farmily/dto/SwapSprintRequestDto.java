package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Family;

import lombok.Data;
import lombok.ToString;
import com.ssafy.farmily.utils.DateRange;

@Data
@ToString
public class SwapSprintRequestDto {
	private DateRange dateRange;
	private Family family;
	private boolean isHarvested;
}
