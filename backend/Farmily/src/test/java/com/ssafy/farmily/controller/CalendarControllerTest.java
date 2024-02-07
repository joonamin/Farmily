package com.ssafy.farmily.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ssafy.farmily.dto.CalendarPlanRequestDto;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.service.calendar.CalendarService;
import com.ssafy.farmily.service.family.FamilyService;
import com.ssafy.farmily.utils.DateRange;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CalendarControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	FamilyService familyService;
	@Autowired
	FamilyRepository familyRepository;
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
	void getCalendarPlan_일정가져오기() throws Exception {
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto dto1 = new MakingFamilyRequestDto("제목", "가훈", null);
		familyService.makeFamily(dto1, member.getUsername());
		Family family = familyRepository.findById(1L).orElseThrow(NoSuchFieldError::new);
		DateRange dateRange = new DateRange();
		dateRange.setStartDate(LocalDate.parse("2023-12-01"));
		dateRange.setEndDate(LocalDate.parse("2023-12-31"));
		CalendarPlanRequestDto dto2 = new CalendarPlanRequestDto();
		dto2.setDateRange(dateRange);
		dto2.setContent("ㅎㅇ");
		dto2.setColor("FF0000");
		dto2.setFamilyId(1L);
		calendarService.postCalendarPlan(dto2, member.getUsername());

		//
		mockMvc.perform(MockMvcRequestBuilders.get("/calendar/1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("Color").value("FF0000"));
	}
}
