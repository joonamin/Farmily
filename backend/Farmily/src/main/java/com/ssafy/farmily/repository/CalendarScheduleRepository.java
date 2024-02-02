package com.ssafy.farmily.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.CalendarSchedule;

public interface CalendarScheduleRepository extends JpaRepository<CalendarSchedule, Long> {
	List<CalendarSchedule> findAllByFamilyId(Long familyId);
}
