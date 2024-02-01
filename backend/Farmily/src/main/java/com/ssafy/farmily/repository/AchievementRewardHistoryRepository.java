package com.ssafy.farmily.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.AchievementRewardHistory;

public interface AchievementRewardHistoryRepository extends JpaRepository<AchievementRewardHistory, Long> {
	List<AchievementRewardHistory> findAllByFamilyIdOrderByIdDesc(Long familyId);
}
