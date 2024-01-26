package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Member;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MakingFamilyRequestDto {
	private String name;
	private String motto;
	private String invitationCode;
	private Member member;

	public MakingFamilyRequestDto(String name, String motto, Member member) {
		this.name = name;
		this.motto = motto;
		this.member = member;
	}
}
