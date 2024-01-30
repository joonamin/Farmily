package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Member;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MakingFamilyRequestDto {
	private String name;
	private String motto;
	private Long memberId;

	public MakingFamilyRequestDto(String name, String motto, Long memberId) {
		this.name = name;
		this.motto = motto;
		this.memberId = memberId;
	}
}
