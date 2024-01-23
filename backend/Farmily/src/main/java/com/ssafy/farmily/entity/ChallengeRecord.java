package com.ssafy.farmily.entity;

import java.util.List;

import com.ssafy.farmily.entity.type.DateRange;
import com.ssafy.farmily.entity.type.RecordType;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("C")
@Getter
@Setter
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
