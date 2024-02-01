package com.ssafy.farmily.dto;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.type.FileCategory;
import com.ssafy.farmily.validation.annotation.AllowedFileCategories;

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

	@NotNull
	@AllowedFileCategories(categories = FileCategory.IMAGE)
	private MultipartFile image;


	public MakingFamilyRequestDto(String name, String motto, MultipartFile image) {
		this.name = name;
		this.motto = motto;
		this.image = image;
	}
}
