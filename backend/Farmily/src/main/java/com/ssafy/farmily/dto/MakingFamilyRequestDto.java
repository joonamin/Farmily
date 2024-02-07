package com.ssafy.farmily.dto;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.type.FileCategory;
import com.ssafy.farmily.validation.annotation.AllowedFileCategories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class MakingFamilyRequestDto {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String motto;

	@NotNull
	@AllowedFileCategories(categories = FileCategory.IMAGE)
	private MultipartFile image;
}
