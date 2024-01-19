package com.ssafy.farmily.entity;

import java.util.List;

import org.w3c.dom.CDATASection;

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

	@Column(columnDefinition = "VARCHAR(16)")
	private String username;

	@Column(columnDefinition = "VARCHAR(32)")
	private String password;

	@Column(columnDefinition = "VARCHAR(16)")
	private String nickname;

	@OneToOne
	@JoinColumn(name = "profilePicId")
	private Image profilePic;


	@OneToMany(mappedBy = "member")
	private List<FamilyMembership> familyMemberships;

	@OneToMany(mappedBy = "author")
	private List<Record> records;
}
