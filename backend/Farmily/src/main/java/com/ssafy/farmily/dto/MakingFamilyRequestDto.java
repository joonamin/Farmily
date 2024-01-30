package com.ssafy.farmily.dto;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MakingFamilyRequestDto {
	private String name;
	private String motto;
	private MultipartFile image;

	public MakingFamilyRequestDto(String name, String motto, MultipartFile image) {
		this.name = name;
		this.motto = motto;
		this.image = image;
	}
}
