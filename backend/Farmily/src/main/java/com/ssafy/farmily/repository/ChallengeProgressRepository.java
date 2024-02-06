package com.ssafy.farmily.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.ChallengeProgress;

public interface ChallengeProgressRepository extends JpaRepository<ChallengeProgress, Long> {
	boolean existsByDateAndId(LocalDate date, Long recordId);
}
