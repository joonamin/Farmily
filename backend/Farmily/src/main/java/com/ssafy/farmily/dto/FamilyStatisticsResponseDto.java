package com.ssafy.farmily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
public class FamilyStatisticsResponseDto {
	private String content;
	private int percent;
	private int rewardPoint;
	private int progress;
	private boolean rewarded;
	private String achievement;
}
