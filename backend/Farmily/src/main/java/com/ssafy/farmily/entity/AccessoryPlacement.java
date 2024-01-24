package com.ssafy.farmily.entity;

import com.ssafy.farmily.type.AccessoryType;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("A")
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class AccessoryPlacement extends Placement {
	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated(EnumType.STRING)
	private AccessoryType type;
}
