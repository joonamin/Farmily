package com.ssafy.farmily.type;

import java.util.function.Function;

import com.ssafy.farmily.entity.FamilyStatistics;
import com.ssafy.farmily.exception.NoSuchContentException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Achievement {
	DAILY_1(FamilyStatistics::getDailyRecordCount, 10, "일상 10개 기록하기", 10),
	DAILY_2(FamilyStatistics::getDailyRecordCount, 20, "일상 20개 기록하기", 20),
	DAILY_3(FamilyStatistics::getDailyRecordCount, 50, "일상 50개 기록하기", 30),
	DAILY_4(FamilyStatistics::getDailyRecordCount, 80, "일상 80개 기록하기", 40),
	DAILY_5(FamilyStatistics::getDailyRecordCount, 100, "일상 100개 기록하기", 50),

	EVENT_1(FamilyStatistics::getEventRecordCount, 10, "이벤트 10개 기록하기", 10),
	EVENT_2(FamilyStatistics::getEventRecordCount, 20, "이벤트 20개 기록하기", 20),
	EVENT_3(FamilyStatistics::getEventRecordCount, 50, "이벤트 50개 기록하기", 30),
	EVENT_4(FamilyStatistics::getEventRecordCount, 80, "이벤트 80개 기록하기", 40),
	EVENT_5(FamilyStatistics::getEventRecordCount, 100, "이벤트 100개 기록하기", 50),

	CHALLENGE_1(FamilyStatistics::getChallengeCompleteCount, 1, "챌린지 기록 1개 완료하기", 10),
	CHALLENGE_2(FamilyStatistics::getChallengeCompleteCount, 2, "챌린지 기록 2개 완료하기", 20),
	CHALLENGE_3(FamilyStatistics::getChallengeCompleteCount, 5, "챌린지 기록 5개 완료하기", 30),
	CHALLENGE_4(FamilyStatistics::getChallengeCompleteCount, 8, "챌린지 기록 8개 완료하기", 40),
	CHALLENGE_5(FamilyStatistics::getChallengeCompleteCount, 10, "챌린지 기록 10개 완료하기", 50),

	CALENDAR_1(FamilyStatistics::getCalendarPlanCount, 10, "일정 10번 공유하기", 10),

	HARVEST_1(FamilyStatistics::getHarvestCount, 1, "1번 수확하기", 20),
	HARVEST_2(FamilyStatistics::getHarvestCount, 3, "3번 수확하기", 30),
	HARVEST_3(FamilyStatistics::getHarvestCount, 5, "5번 수확하기", 50);

	private Function<FamilyStatistics, Integer> getter;
	private float goal;
	private String content;
	private int reward;

	public static Achievement of(String code){
		for(Achievement a : Achievement.values()){
			return a;
		}

		throw new NoSuchContentException("일치하는 도전과제가 없습니다.");
	}
}