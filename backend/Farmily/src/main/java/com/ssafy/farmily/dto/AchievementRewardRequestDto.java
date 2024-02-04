package com.ssafy.farmily.dto;

import com.ssafy.farmily.type.Achievement;
import com.ssafy.farmily.validation.annotation.EnumValue;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AchievementRewardRequestDto {
	@NotNull
	private Long familyId;
	@EnumValue(enumClass = Achievement.class)
	private String achievement;
}
