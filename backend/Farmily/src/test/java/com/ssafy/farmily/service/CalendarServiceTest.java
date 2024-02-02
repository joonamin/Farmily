package com.ssafy.farmily.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.farmily.dto.CalendarPlanRequestDto;
import com.ssafy.farmily.dto.CalendarPlanResponseDto;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.service.calendar.CalendarService;
import com.ssafy.farmily.service.family.FamilyService;
import com.ssafy.farmily.utils.DateRange;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("local")
public class CalendarServiceTest {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	FamilyService familyService;
	@Autowired
	CalendarService calendarService;

	@BeforeEach
	public void beforeEach() {
		Member member = Member.builder()
			.nickname("salah")
			.username("KAKAO_156156")
			.password("12345678")
			.familyMemberships(new LinkedList<>())
			.build();
		memberRepository.save(member);
	}

	@Test
	@Transactional
	void postCalendarPlan_일정게시_후_일정확인() {
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto dto1 = new MakingFamilyRequestDto("제목", "가훈", null);
		familyService.makeFamily(dto1, member.getUsername());
		DateRange dateRange = new DateRange();
		dateRange.setStartDate(LocalDate.parse("2023-12-01"));
		dateRange.setEndDate(LocalDate.parse("2023-12-31"));
		CalendarPlanRequestDto dto2 = new CalendarPlanRequestDto();
		dto2.setDateRange(dateRange);
		dto2.setContent("ㅎㅇ");
		dto2.setColor("T12142");
		dto2.setFamilyId(1L);
		calendarService.postCalendarPlan(dto2, member.getUsername());
		List<CalendarPlanResponseDto> list = calendarService.getCalendarPlanList(1L, member.getUsername());

		Assertions.assertEquals(list.get(0).getContent(), "ㅎㅇ");
	}
}
