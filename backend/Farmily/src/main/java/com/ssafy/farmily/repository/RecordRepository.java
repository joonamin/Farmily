package com.ssafy.farmily.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
	@Query(value = """
	SELECT c
	  FROM ChallengeRecord c
		JOIN Sprint s
		JOIN Family f
	 WHERE s.isHarvested = false AND f.id = :familyId""")
	List<ChallengeRecord> findCurrentChallenges(Long familyId);

	Page<Record> findAllBySprintOrderByIdDesc(Sprint sprint, Pageable pageable);
}
