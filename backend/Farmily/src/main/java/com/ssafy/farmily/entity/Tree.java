package com.ssafy.farmily.entity;

import java.util.List;

import net.minidev.json.annotate.JsonIgnore;

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
public class Tree extends BaseEntity {
	@Id
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	@MapsId
	@JsonIgnore
	private Family family;

	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated(EnumType.STRING)
	private TreeType type;

	@OneToMany(mappedBy = "tree")
	private List<Placement> placements;
}
