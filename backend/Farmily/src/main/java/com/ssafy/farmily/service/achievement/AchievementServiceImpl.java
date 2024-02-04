package com.ssafy.farmily.service.achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.AchievementRewardRequestDto;
import com.ssafy.farmily.dto.AchievementRewardResponseDto;
import com.ssafy.farmily.dto.FamilyAchievementProgressDto;
import com.ssafy.farmily.dto.FamilyStatisticsResponseDto;
import com.ssafy.farmily.entity.AchievementRewardHistory;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyStatistics;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.AchievementRewardHistoryRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.FamilyStatisticsRepository;
import com.ssafy.farmily.service.family.FamilyService;
import com.ssafy.farmily.type.Achievement;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

	private final FamilyStatisticsRepository familyStatisticsRepository;
	private final AchievementRewardHistoryRepository achievementRewardHistoryRepository;
	private final FamilyRepository familyRepository;
	private final FamilyService familyService;

	@Override
	public List<FamilyStatisticsResponseDto> familyAchievementProgress(Long familyId) {
		FamilyStatistics familyStatistics = familyStatisticsRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("유효하지 않은 가족입니다."));
		FamilyAchievementProgressDto progressDto = FamilyAchievementProgressDto.from(familyStatistics);
		List<Achievement> receivedRewardChallenge = from(
			achievementRewardHistoryRepository.findAllByFamilyIdOrderByIdDesc(familyId));
		progressDto.setReceivedRewardChallenge(receivedRewardChallenge);

		List<FamilyStatisticsResponseDto> responseDtoList = new ArrayList<>();
		for (Achievement achievement : Achievement.values()) {
			int progress = achievement.getGetter().apply(familyStatistics);
			int rewardPoint = achievement.getReward();
			float goal = achievement.getGoal();
			String content = achievement.getContent();

			int percent = 0;
			if (progress >= goal) {
				progress = (int)goal;
				percent = 100;
			} else {
				percent = Math.round((progress / goal) * 100);
			}

			FamilyStatisticsResponseDto responseDto = FamilyStatisticsResponseDto.builder()
				.content(content)
				.rewardPoint(rewardPoint)
				.percent(percent)
				.progress(progress)
				.rewarded(false)
				.build();
			if (progressDto.getReceivedRewardChallenge().contains(achievement))
				responseDto.setRewarded(true);
			responseDtoList.add(responseDto);
		}
		return responseDtoList;
	}

	@Override
	public AchievementRewardResponseDto receiveReward(AchievementRewardRequestDto dto, String username) {
		Long familyId = dto.getFamilyId();
		familyService.assertMembership(familyId, username);

		Family family = familyRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("유효하지 않은 가족입니다."));

		String achievement = dto.getAchievement();
		Achievement RewardForAchievement = Achievement.of(achievement);
		int point = family.getPoint();
		point += RewardForAchievement.getReward();
		family.setPoint(point);
		Integer now = RewardForAchievement.getReward();

		if (RewardForAchievement.getGoal() > now) {
			throw new BusinessException("요구조건을 만족하지 않았습니다.");
		}

		familyRepository.save(family);
		AchievementRewardHistory achievementRewardHistory = AchievementRewardHistory.builder()
			.achievement(RewardForAchievement)
			.family(family)
			.build();
		achievementRewardHistoryRepository.save(achievementRewardHistory);
		AchievementRewardResponseDto responseDto = new AchievementRewardResponseDto();
		responseDto.setPoint(point);
		return responseDto;
	}

	private List<Achievement> from(List<AchievementRewardHistory> achievementRewardHistoryList) {
		List<Achievement> getAchievementList = new ArrayList<>();
		for (AchievementRewardHistory entity : achievementRewardHistoryList) {
			getAchievementList.add(entity.getAchievement());
		}
		return getAchievementList;
	}
}
