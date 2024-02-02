package com.ssafy.farmily.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.CalendarPlanRequestDto;
import com.ssafy.farmily.dto.CalendarPlanResponseDto;
import com.ssafy.farmily.service.calendar.CalendarService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {
	private final CalendarService calendarService;

	@PostMapping("")
	public ResponseEntity<Void> postCalendarPlan(
		@Valid @RequestBody CalendarPlanRequestDto dto,
		@AuthenticationPrincipal String username) {
		calendarService.postCalendarPlan(dto, username);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{familyId}")
	public ResponseEntity<List<CalendarPlanResponseDto>> getCalendarPlan(
		@PathVariable Long familyId,
		@AuthenticationPrincipal String username) {
		List<CalendarPlanResponseDto> calendarPlanList = calendarService.getCalendarPlanList(familyId, username);
		return ResponseEntity.ok(calendarPlanList);
	}
}
