package com.ssafy.farmily.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.Placement;

public interface PlacementRepository extends JpaRepository<Placement, Long> {

	List<Placement> findByTreeId(Long treeId);

	void deleteAllByTreeId(Long treeId);
}
