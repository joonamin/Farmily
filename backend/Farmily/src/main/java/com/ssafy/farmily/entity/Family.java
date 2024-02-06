package com.ssafy.farmily.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
	private List<FamilyItem> items;

	@OneToOne(mappedBy = "family")
	@PrimaryKeyJoinColumn
	private FamilyStatistics statistics;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Tree tree;

	@OneToMany(mappedBy = "family")
	private List<CalendarSchedule> calendarSchedules;

	@OneToMany(mappedBy = "family")
	private List<AchievementRewardHistory> achievementRewardHistories;

	@OneToMany(mappedBy = "family")
	private List<Sprint> sprints;

	@OneToOne(mappedBy = "family", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private FamilyFruitSkins fruitSkins;
}
