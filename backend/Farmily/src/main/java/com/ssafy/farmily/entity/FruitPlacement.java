package com.ssafy.farmily.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("F")
public class FruitPlacement extends Placement {
	@OneToOne
	@JoinColumn(name = "recordId")
	private Record record;
}
