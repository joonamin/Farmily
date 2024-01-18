package com.ssafy.farmily.entity;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private String nickname;

	@OneToOne
	@JoinColumn(name = "profilePicId")
	private Image profilePic;


	@OneToMany(mappedBy = "member")
	private Collection<FamilyMembership> familyMemberships;

	@OneToMany(mappedBy = "author")
	private Collection<Record> records;
}
