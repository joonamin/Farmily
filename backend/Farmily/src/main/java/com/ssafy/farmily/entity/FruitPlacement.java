package com.ssafy.farmily.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("F")
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class FruitPlacement extends Placement {
	@OneToOne
	@JoinColumn(name = "recordId")
	private Record record;
}
