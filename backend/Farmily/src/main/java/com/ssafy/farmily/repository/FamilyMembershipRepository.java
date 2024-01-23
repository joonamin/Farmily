package com.ssafy.farmily.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.FamilyMembership;

public interface FamilyMembershipRepository extends JpaRepository<FamilyMembership, Long> {
	Optional<FamilyMembership> findByFamilyIdAndMemberId(Long familyId, Long memberId);
}
