package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.FamilyStatistics;

public interface FamilyStatisticsRepository extends JpaRepository<FamilyStatistics, Long> {
}
