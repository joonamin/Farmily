package com.ssafy.farmily.entity;

import com.ssafy.farmily.code.Item;
import com.ssafy.farmily.code.ItemType;

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
public class FamilyItem extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "familyId")
	private Family family;

	@Column
	@Enumerated
	private Item code;

	@Column
	@Enumerated
	private ItemType type;
}
