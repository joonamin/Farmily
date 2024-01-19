package com.ssafy.farmily.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class ImageCard extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "imageId")
	private Image image;

	@ManyToOne
	@JoinColumn(name = "recordId")
	private Record event;

	@Column(columnDefinition = "VARCHAR(255)")
	private String description;
}
