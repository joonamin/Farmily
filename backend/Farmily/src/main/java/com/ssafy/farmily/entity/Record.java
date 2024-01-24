package com.ssafy.farmily.entity;

import java.util.List;

import com.ssafy.farmily.type.RecordType;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(columnDefinition = "CHAR(1)")
@DiscriminatorValue("-")
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
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

	@Column(columnDefinition = "VARCHAR(32)")
	@Enumerated(EnumType.STRING)
	private RecordType type;
}
