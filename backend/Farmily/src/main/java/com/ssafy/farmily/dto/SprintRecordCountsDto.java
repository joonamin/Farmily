package com.ssafy.farmily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SprintRecordCountsDto {
	private Integer daily;
	private Integer challenge;
	private Integer event;
}
