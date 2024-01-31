package com.ssafy.farmily.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.Placement;
@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {

	List<Placement> findAllByTreeId(Long treeId);

	void deleteAllByTreeId(Long treeId);
}
