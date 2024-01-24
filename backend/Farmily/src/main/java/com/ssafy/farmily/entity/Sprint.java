package com.ssafy.farmily.entity;

import java.util.List;

import net.minidev.json.annotate.JsonIgnore;

import utils.DateRange;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Sprint extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "familyId")
	@JsonIgnore
	private Family family;

	@Embedded
	private DateRange dateRange;

	@Column(columnDefinition = "BOOLEAN")
	private Boolean isHarvested;


	@OneToMany(mappedBy = "sprint")
	private List<Record> records;
}
