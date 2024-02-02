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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter
@Builder
@Setter
@AllArgsConstructor
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "VARCHAR(32)")
	private String username;

	@Column(columnDefinition = "VARCHAR(32)")
	private String password;

	@Column(columnDefinition = "VARCHAR(16)")
	private String nickname;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profilePicId")
	private Image profilePic;

	@OneToMany(mappedBy = "member")
	private List<FamilyMembership> familyMemberships;

	@OneToMany(mappedBy = "author")
	private List<Record> records;
}
