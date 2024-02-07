package com.ssafy.farmily.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.dto.FamilyInventoryRecordResponseDtoInterface;
import com.ssafy.farmily.dto.SprintRecordCountsDto;
import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
	@Query("""
		SELECT c
		  FROM ChallengeRecord c
			JOIN c.sprint s
			JOIN s.family f
		 WHERE c.isRewarded = false AND s.isHarvested = false AND f.id = :familyId
	""")
	List<ChallengeRecord> findCurrentChallenges(Long familyId);

	Page<Record> findAllBySprintOrderByIdDesc(Sprint sprint, Pageable pageable);

	@Query(value = """
		SELECT a.id,a.title,a.type
		FROM 
			(SELECT a.id,a.title,a.type
			FROM record a LEFT JOIN 
				(SELECT * FROM 
				challenge_record 
				WHERE is_rewarded = FALSE) b 
			ON a.id = b.id 
			WHERE b.id IS NULL AND a.sprint_id = :sprintId) a 
		LEFT JOIN placement b 
		ON a.id = b.record_id 
		WHERE b.record_id is NULL
		""", nativeQuery = true)
	List<FamilyInventoryRecordResponseDtoInterface> findRecordInInventory(Long sprintId);

	@Query("""
		SELECT		r.type AS type, COUNT(r.id) AS count
		FROM		Sprint s JOIN s.records r
		WHERE		s.id = :sprintId
		GROUP BY	r.type
	""")
	List<SprintRecordCountsDto.Tuple> countRecordGroupByRecordType(Long sprintId);
}
