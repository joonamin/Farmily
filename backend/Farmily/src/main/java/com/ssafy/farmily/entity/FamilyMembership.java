package com.ssafy.farmily.entity;

import org.w3c.dom.CDATASection;

import com.ssafy.farmily.entity.type.FamilyRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
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
