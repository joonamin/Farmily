package com.ssafy.farmily.service.calendar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.aop.annotation.Statistics;
import com.ssafy.farmily.dto.CalendarPlanRequestDto;
import com.ssafy.farmily.dto.CalendarPlanResponseDto;
import com.ssafy.farmily.dto.ServiceProcessResult;
import com.ssafy.farmily.entity.CalendarSchedule;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyStatistics;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.CalendarScheduleRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.service.family.FamilyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {
	private final FamilyRepository familyRepository;
	private final CalendarScheduleRepository calendarScheduleRepository;

	private final FamilyService familyService;

	@Override
	@Statistics(FamilyStatistics.Field.CALENDAR_PLAN_COUNT)
	@Transactional
	public ServiceProcessResult postCalendarPlan(CalendarPlanRequestDto dto, String username) {
		familyService.assertMembership(dto.getFamilyId(), username);
		Family family = familyRepository.findById(dto.getFamilyId()).orElseThrow(
			() -> new NoSuchContentException("유효하지 않은 가족입니다.")
		);
		String color = dto.getColor().toUpperCase();
		CalendarSchedule entity = CalendarSchedule.builder()
			.dateRange(dto.getDateRange())
			.color(color)
			.memo(dto.getContent())
			.family(family)
			.build();
		calendarScheduleRepository.save(entity);

		return new ServiceProcessResult(dto.getFamilyId());
	}

	@Override
	public List<CalendarPlanResponseDto> getCalendarPlanList(Long familyId, String username) {
		familyService.assertMembership(familyId, username);
		Family family = familyRepository.findById(familyId).orElseThrow(
			() -> new NoSuchContentException("유효하지 않은 가족입니다.")
		);

		List<CalendarPlanResponseDto> CalendarPlanList = new ArrayList<>();
		List<CalendarSchedule> familySchedule = calendarScheduleRepository.findAllByFamilyId(familyId);
		for (CalendarSchedule entity : familySchedule) {
			CalendarPlanResponseDto dto = CalendarPlanResponseDto.builder()
				.dateRange(entity.getDateRange())
				.color(entity.getColor())
				.content(entity.getMemo())
				.id(entity.getId())
				.build();
			CalendarPlanList.add(dto);
		}
		return CalendarPlanList;
	}
}
