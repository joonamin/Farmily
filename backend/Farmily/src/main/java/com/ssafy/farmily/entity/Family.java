package com.ssafy.farmily.entity;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;

@Entity
@Getter
public class Family extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String motto;

	@Column
	private String invitationCode;

	@OneToOne
	@JoinColumn(name = "imageId")
	private Image image;

	@Column
	private Integer point;


	@OneToMany(mappedBy = "family")
	private Collection<FamilyItem> items;

	@OneToOne(mappedBy = "family")
	@PrimaryKeyJoinColumn
	private FamilyStatistics statistics;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Tree tree;

	@OneToMany(mappedBy = "family")
	private Collection<CalendarSchedule> calendarSchedules;

	@OneToMany(mappedBy = "family")
	private Collection<AchievementRewardHistory> achievementRewardHistories;

	@OneToMany(mappedBy = "family")
	private Collection<Sprint> sprints;
}
