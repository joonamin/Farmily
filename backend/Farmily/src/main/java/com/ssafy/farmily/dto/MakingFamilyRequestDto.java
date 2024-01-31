package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MakingFamilyRequestDto {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String motto;
	
	// TODO: Entity가 아닌 타입을 참조
	private Member member;

	public MakingFamilyRequestDto(String name, String motto, Member member) {
		this.name = name;
		this.motto = motto;
		this.member = member;
	}
}
