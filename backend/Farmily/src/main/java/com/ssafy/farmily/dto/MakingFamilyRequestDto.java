package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MakingFamilyRequestDto {
	private String name;
	private String motto;
	private Image image;

	public MakingFamilyRequestDto(String name, String motto, Image image) {
		this.name = name;
		this.motto = motto;
		this.image = image;
	}
}
