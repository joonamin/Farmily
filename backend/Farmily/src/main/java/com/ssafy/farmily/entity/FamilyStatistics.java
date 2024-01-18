package com.ssafy.farmily.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class FamilyStatistics extends BaseEntity {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	private Family family;

	@Column
	private Integer fruitCount;

	@Column
	private Integer conferenceCount;

	@Column
	private Integer chatCount;

	@Column
	private Integer treeChangeCount;

	@Column
	private Integer recordCount;

	@Column
	private Integer recordStreak;

	@Column
	private Integer commentCount;

	@Column
	private Integer communityPostCount;

	@Column
	private Integer accessoryCount;

	@Column
	private Integer harvestCount;
}
