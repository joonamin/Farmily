package com.ssafy.farmily.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.ssafy.farmily.entity.Sprint;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MainSprintResponseDto {
	Long sprintId;
	LocalDate endDate;

	public static MainSprintResponseDto from(Sprint sprint) {
		MainSprintResponseDto mainSprintResponseDto = new MainSprintResponseDto();
		mainSprintResponseDto.setSprintId(sprint.getId());
		mainSprintResponseDto.setEndDate(sprint.getDateRange().getEndDate());
		return mainSprintResponseDto;
	}
}
