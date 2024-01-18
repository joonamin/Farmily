package com.ssafy.farmily.entity;

import java.util.Collection;

import com.ssafy.farmily.type.DateRange;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@DiscriminatorValue("C")
@Getter
public class ChallengeRecord extends Record {
	@Embedded
	private DateRange range;

	@Column
	private Boolean rewarded;

	@OneToMany(mappedBy = "challenge")
	private Collection<ChallengeProgress> progresses;
}
