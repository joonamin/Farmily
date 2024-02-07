package com.ssafy.farmily.type;

import com.ssafy.farmily.entity.FamilyStatistics.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Achievement {
	DAILY_1(Field.DAILY_RECORD_COUNT, 10, "일상 10개 기록하기", 10,"DAILY_1"),
	DAILY_2(Field.DAILY_RECORD_COUNT, 20, "일상 20개 기록하기", 20,"DAILY_2"),
	DAILY_3(Field.DAILY_RECORD_COUNT, 50, "일상 50개 기록하기", 30,"DAILY_3"),
	DAILY_4(Field.DAILY_RECORD_COUNT, 80, "일상 80개 기록하기", 40,"DAILY_4"),
	DAILY_5(Field.DAILY_RECORD_COUNT, 100, "일상 100개 기록하기", 50,"DAILY_5"),

	EVENT_1(Field.EVENT_RECORD_COUNT, 10, "이벤트 10개 기록하기", 10,"EVENT_1"),
	EVENT_2(Field.EVENT_RECORD_COUNT, 20, "이벤트 20개 기록하기", 20,"EVENT_2"),
	EVENT_3(Field.EVENT_RECORD_COUNT, 50, "이벤트 50개 기록하기", 30,"EVENT_3"),
	EVENT_4(Field.EVENT_RECORD_COUNT, 80, "이벤트 80개 기록하기", 40,"EVENT_4"),
	EVENT_5(Field.EVENT_RECORD_COUNT, 100, "이벤트 100개 기록하기", 50,"EVENT_5"),

	CHALLENGE_1(Field.CHALLENGE_COMPLETE_COUNT, 1, "챌린지 기록 1개 완료하기", 10,"CHALLENGE_1"),
	CHALLENGE_2(Field.CHALLENGE_COMPLETE_COUNT, 2, "챌린지 기록 2개 완료하기", 20,"CHALLENGE_2"),
	CHALLENGE_3(Field.CHALLENGE_COMPLETE_COUNT, 5, "챌린지 기록 5개 완료하기", 30,"CHALLENGE_3"),
	CHALLENGE_4(Field.CHALLENGE_COMPLETE_COUNT, 8, "챌린지 기록 8개 완료하기", 40,"CHALLENGE_4"),
	CHALLENGE_5(Field.CHALLENGE_COMPLETE_COUNT, 10, "챌린지 기록 10개 완료하기", 50,"CHALLENGE_5"),

	CALENDAR_1(Field.CALENDAR_PLAN_COUNT, 10, "일정 10번 공유하기", 10,"CALENDAR_1"),

	HARVEST_1(Field.HARVEST_COUNT, 1, "1번 수확하기", 20,"HARVEST_1"),
	HARVEST_2(Field.HARVEST_COUNT, 3, "3번 수확하기", 30,"HARVEST_2"),
	HARVEST_3(Field.HARVEST_COUNT, 5, "5번 수확하기", 50,"HARVEST_3");

	private final Field field;
	private final float goal;
	private final String content;
	private final int reward;
	private final String achievement;
}