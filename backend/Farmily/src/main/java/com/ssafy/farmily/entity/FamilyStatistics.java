package com.ssafy.farmily.entity;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.ssafy.farmily.repository.FamilyStatisticsRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FamilyStatistics extends BaseEntity {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	private Family family;

	@Column
	private Integer dailyRecordCount;

	@Column
	private Integer eventRecordCount;

	@Column
	private Integer challengeCompleteCount;

	@Column
	private Integer harvestCount;

	@Column
	private Integer calendarPlanCount;

	@Column
	private boolean isFirstConference;


	@AllArgsConstructor
	public enum Field {
		DAILY_RECORD_COUNT(
			FamilyStatistics::getDailyRecordCount,
			FamilyStatisticsRepository::incrementDailyRecordCount
		),
		EVENT_RECORD_COUNT(
			FamilyStatistics::getEventRecordCount,
			FamilyStatisticsRepository::incrementEventRecordCount
		),
		CHALLENGE_COMPLETE_COUNT(
			FamilyStatistics::getChallengeCompleteCount,
			FamilyStatisticsRepository::incrementChallengeCompleteCount
		),
		HARVEST_COUNT(
			FamilyStatistics::getHarvestCount,
			FamilyStatisticsRepository::incrementHarvestCount
		),
		CALENDAR_PLAN_COUNT(
			FamilyStatistics::getCalendarPlanCount,
			FamilyStatisticsRepository::incrementCalendarPlanCount
		),
		;

		@Getter
		private Function<FamilyStatistics, Integer> getter;

		private BiConsumer<FamilyStatisticsRepository, Long> incrementer;

		public void increment(FamilyStatisticsRepository familyStatisticsRepository, Long familyId) {
			incrementer.accept(familyStatisticsRepository, familyId);
		}
	}
}
