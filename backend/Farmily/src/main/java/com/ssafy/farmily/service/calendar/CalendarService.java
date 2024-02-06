package com.ssafy.farmily.service.calendar;

import java.io.Serializable;
import java.util.List;

import com.ssafy.farmily.dto.CalendarPlanRequestDto;
import com.ssafy.farmily.dto.CalendarPlanResponseDto;
import com.ssafy.farmily.dto.ServiceProcessResult;

public interface CalendarService {
	ServiceProcessResult postCalendarPlan(CalendarPlanRequestDto dto, String username);

	List<CalendarPlanResponseDto> getCalendarPlanList(Long familyId, String username);
}
