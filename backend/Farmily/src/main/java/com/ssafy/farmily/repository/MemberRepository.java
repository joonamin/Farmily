package com.ssafy.farmily.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.dto.FamilyMemberResponseDto;
import com.ssafy.farmily.entity.Member;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);


	@Query(
		value = "select new com.ssafy.farmily.dto.FamilyMemberResponseDto(b.id,b.nickname,a.role) " +
		"from FamilyMembership a " +
		"join member b on a.member.id = b.id " +
		"where a.family.id = :familyId"
		)
	List<FamilyMemberResponseDto> findAllByFamilyId(Long familyId);
}
