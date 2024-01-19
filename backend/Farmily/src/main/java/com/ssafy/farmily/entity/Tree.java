package com.ssafy.farmily.entity;

import java.util.List;

import com.ssafy.farmily.entity.type.TreeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Tree extends BaseEntity {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	private Family family;

	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated(EnumType.STRING)
	private TreeType type;

	@OneToMany(mappedBy = "tree")
	private List<Placement> placements;
}
