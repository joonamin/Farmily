package com.ssafy.farmily.dto;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.type.FileCategory;
import com.ssafy.farmily.validation.annotation.AllowedFileCategories;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "요청 시 이미지 카드 DTO")
public class ImageCardRequestDto {
	@NotNull
	@AllowedFileCategories(categories = FileCategory.IMAGE)
	private MultipartFile imageFile;

	private String description;
}
