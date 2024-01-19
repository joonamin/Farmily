package com.ssafy.farmily.entity;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(columnDefinition = "CHAR(1)")
@Getter
public class Record extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "sprintId")
	private Sprint sprint;

	@Column(columnDefinition = "VARCHAR(255)")
	private String title;

	@ManyToOne
	@JoinColumn(name = "authorId")
	private Member author;

	@Column(columnDefinition = "TEXT")
	private String content;

	@OneToMany(mappedBy = "record")
	private List<Comment> comments;


	@OneToMany(mappedBy = "record")
	private List<ImageCard> imageCards;
}
