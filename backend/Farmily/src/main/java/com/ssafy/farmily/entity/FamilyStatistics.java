package com.ssafy.farmily.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FamilyStatistics extends BaseEntity {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	private Family family;

	@Column
	private Integer dailyRecordCount;

	@Column
	private Integer eventRecordCount;

	@Column
	private Integer challengeCompleteCount;

	@Column
	private Integer harvestCount;

	@Column
	private Integer calendarPlanCount;

	@Column
	private boolean isFirstConference;
}
