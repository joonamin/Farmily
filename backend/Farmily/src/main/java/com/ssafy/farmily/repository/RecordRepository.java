package com.ssafy.farmily.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
	@Query(value = """
	SELECT a.id
		FROM challenge_record a
		JOIN record b ON a.id = b.id
		JOIN sprint c ON b.sprint_id = c.id
		WHERE c.is_harvested = false
		AND c.family_id = :familyId""", nativeQuery = true)
	List<Long> findCurrentChallenges(Long familyId);

	List<Record> findAllBySprint(Sprint sprint);
}
