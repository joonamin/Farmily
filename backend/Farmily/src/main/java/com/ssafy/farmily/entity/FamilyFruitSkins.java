package com.ssafy.farmily.entity;

import com.ssafy.farmily.type.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyFruitSkins {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	private Family family;

	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated(EnumType.STRING)
	private Item daily;

	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated(EnumType.STRING)
	private Item challenge;

	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated(EnumType.STRING)
	private Item event;
}
