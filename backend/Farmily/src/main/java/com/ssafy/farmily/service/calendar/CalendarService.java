package com.ssafy.farmily.service.calendar;

import java.util.List;

import com.ssafy.farmily.dto.CalendarPlanRequestDto;
import com.ssafy.farmily.dto.CalendarPlanResponseDto;

public interface CalendarService {
	void postCalendarPlan(CalendarPlanRequestDto dto, String username);

	List<CalendarPlanResponseDto> getCalendarPlanList(Long familyId, String username);
}
