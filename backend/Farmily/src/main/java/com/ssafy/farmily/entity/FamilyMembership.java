package com.ssafy.farmily.entity;

import com.ssafy.farmily.entity.type.FamilyRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FamilyMembership extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "familyId")
	private Family family;

	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated
	private FamilyRole role;
}
