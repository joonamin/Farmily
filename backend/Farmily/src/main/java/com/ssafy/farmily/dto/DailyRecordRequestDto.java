package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DailyRecordRequestDto {
	private Long sprintId;
	private Long authorId;
	private String title;
	private String content;
}
