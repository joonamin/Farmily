package com.ssafy.farmily.dto;

import java.util.List;

import com.ssafy.farmily.entity.FamilyStatistics;
import com.ssafy.farmily.type.Achievement;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FamilyAchievementProgressDto {
	private int dailyRecordCount;
	private int eventRecordCount;
	private int challengeCompleteCount;
	private int harvestCount;
	private int calendarPlanCount;
	private boolean isFirstConference;

	private List<Achievement> receivedRewardChallenge;

	public static FamilyAchievementProgressDto from(FamilyStatistics familyStatistics){
		FamilyAchievementProgressDto responseDto = new FamilyAchievementProgressDto();
		responseDto.setDailyRecordCount(familyStatistics.getDailyRecordCount());
		responseDto.setEventRecordCount(familyStatistics.getEventRecordCount());
		responseDto.setChallengeCompleteCount(familyStatistics.getChallengeCompleteCount());
		responseDto.setHarvestCount(familyStatistics.getHarvestCount());
		responseDto.setCalendarPlanCount(familyStatistics.getCalendarPlanCount());
		responseDto.isFirstConference = familyStatistics.isFirstConference();
		return responseDto;
	}
}
