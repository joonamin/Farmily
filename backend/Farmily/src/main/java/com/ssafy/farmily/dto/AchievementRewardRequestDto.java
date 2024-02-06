package com.ssafy.farmily.dto;

import com.ssafy.farmily.type.Achievement;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AchievementRewardRequestDto {
	@NotNull
	private Long familyId;
	@NotNull
	private Achievement achievement;
}
