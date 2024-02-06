package com.ssafy.farmily.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
	List<Sprint> findAllByFamilyIdAndIsHarvestedOrderByIdDesc(Long familyId, Boolean isHarvested);

	Optional<Sprint> findByFamilyIdAndIsHarvested(Long familyId, Boolean isHarvested);

	Sprint findByFamilyId(Long familyId);
}
