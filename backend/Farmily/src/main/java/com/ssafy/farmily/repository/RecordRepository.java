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
	/*
	커스텀 동적 쿼리로 작성할 때
	그냥 작성한 쿼리문을 그대로 db에 입력하는 형태라
	카멜형식으로 작성하면 쿼리문을 실행 못하네영!
	*/
	@Query(value = """
	SELECT a.id
		FROM challenge_record a
		JOIN record b ON a.id = b.id
		JOIN sprint c ON b.sprint_id = c.id
		WHERE curdate() BETWEEN c.start_date AND c.end_date
		AND c.family_id = :familyId""", nativeQuery = true)
	List<Long> findCurrentChallenges(@Param("familyId") Long familyId);

	List<Record> findAllBySprint(Sprint sprint);
}
