package com.ssafy.farmily.entity;

import java.util.Collection;

import com.ssafy.farmily.type.DateRange;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Sprint extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "familyId")
	private Family family;

	@Embedded
	private DateRange range;

	@Column
	private Boolean harvested;


	@OneToMany(mappedBy = "sprint")
	private Collection<Record> records;
}
