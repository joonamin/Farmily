package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.farmily.entity.FamilyStatistics;

public interface FamilyStatisticsRepository extends JpaRepository<FamilyStatistics, Long> {

	@Query("UPDATE FamilyStatistics f SET f.calendarPlanCount = f.calendarPlanCount + 1 WHERE f.family.id = :familyId")
	void incrementCalendarPlanCount(Long familyId);

	@Query("UPDATE FamilyStatistics f SET f.dailyRecordCount = f.dailyRecordCount + 1 WHERE f.family.id = :familyId")
	void incrementDailyRecordCount(Long familyId);

	@Query("UPDATE FamilyStatistics f SET f.eventRecordCount = f.eventRecordCount + 1 WHERE f.family.id = :familyId")
	void incrementEventRecordCount(Long familyId);

	@Query("UPDATE FamilyStatistics f SET f.challengeCompleteCount = f.challengeCompleteCount + 1 WHERE f.family.id = :familyId")
	void incrementChallengeCompleteCount(Long familyId);

	@Query("UPDATE FamilyStatistics f SET f.harvestCount = f.harvestCount + 1 WHERE f.family.id = :familyId")
	void incrementHarvestCount(Long familyId);
}
