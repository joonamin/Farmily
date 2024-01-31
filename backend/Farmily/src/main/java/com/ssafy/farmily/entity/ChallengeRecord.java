package com.ssafy.farmily.entity;

import java.util.List;

import lombok.RequiredArgsConstructor;
import com.ssafy.farmily.utils.DateRange;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("C")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ChallengeRecord extends Record {
	@Embedded
	private DateRange dateRange;

	@Column(columnDefinition = "BOOLEAN")
	private Boolean isRewarded;

	@OneToMany(mappedBy = "challenge")
	private List<ChallengeProgress> progresses;
}
