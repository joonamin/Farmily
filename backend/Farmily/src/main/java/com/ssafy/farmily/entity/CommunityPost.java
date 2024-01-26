package com.ssafy.farmily.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class CommunityPost extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "VARCHAR(255)")
	private String title;

	@ManyToOne
	@JoinColumn(name = "authorId")
	private Member author;

	@Column(columnDefinition = "TEXT")
	private String content;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "treeImageId")
	private Image treeImage;

	// 그냥 게시글에 올라온 사진과 content만 띄어준다고해서 sprint는 따로 필요 없을 것 같아요
	// @ManyToOne
	// @JoinColumn(name = "sprintId")
	// private Sprint sprint;
}
