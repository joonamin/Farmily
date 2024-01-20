package com.ssafy.farmily.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.FamilyItem;

public interface FamilyItemRepository extends JpaRepository<FamilyItem, Long> {
	List<FamilyItem> findByFamilyId(Long familyId);
}
