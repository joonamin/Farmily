package com.ssafy.farmily.service.achievement;

import java.util.List;

import com.ssafy.farmily.dto.AchievementRewardRequestDto;
import com.ssafy.farmily.dto.AchievementRewardResponseDto;
import com.ssafy.farmily.dto.FamilyStatisticsResponseDto;

public interface AchievementService {
	public List<FamilyStatisticsResponseDto> familyAchievementProgress(Long familyId);

	public AchievementRewardResponseDto receiveReward(AchievementRewardRequestDto dto, String username);
}
