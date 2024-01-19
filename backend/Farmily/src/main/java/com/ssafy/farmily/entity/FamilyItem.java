package com.ssafy.farmily.entity;

import com.ssafy.farmily.entity.type.Item;
import com.ssafy.farmily.entity.type.ItemType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class FamilyItem extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "familyId")
	private Family family;

	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated(EnumType.STRING)
	private Item code;

	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated(EnumType.STRING)
	private ItemType type;
}
