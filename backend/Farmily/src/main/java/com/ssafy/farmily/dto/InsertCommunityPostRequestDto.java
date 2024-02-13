package com.ssafy.farmily.dto;

import java.util.Locale;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.type.FileCategory;
import com.ssafy.farmily.validation.annotation.AllowedFileCategories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InsertCommunityPostRequestDto {
	@NotBlank
	String title;

	@NotBlank
	String content;

	@AllowedFileCategories(categories = FileCategory.IMAGE)
	MultipartFile treeSnapshot;
}
