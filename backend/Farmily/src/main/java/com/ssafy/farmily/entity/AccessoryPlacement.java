package com.ssafy.farmily.entity;

import com.ssafy.farmily.code.AccessoryType;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("A")
public class AccessoryPlacement extends Placement {
	@Column
	@Enumerated(EnumType.STRING)
	private AccessoryType type;
}
