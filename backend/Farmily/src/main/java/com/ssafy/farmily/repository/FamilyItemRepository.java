package com.ssafy.farmily.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.type.Item;

@Repository
public interface FamilyItemRepository extends JpaRepository<FamilyItem, Long> {
	List<FamilyItem> findByFamilyId(Long familyId);
	List<FamilyItem> findAllByFamilyId(Long familyId);
	boolean existsByCodeAndFamilyId(Item item, Long familyId);
}
