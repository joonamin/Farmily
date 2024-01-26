package com.ssafy.farmily.dto;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.entity.ImageCard;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "응답 시 이미지 카드 DTO")
public class ImageCardResponseDto {
	private ImageDto image;
	private String description;

	public static ImageCardResponseDto from(ImageCard imageCard) {
		return ImageCardResponseDto.builder()
			.image(ImageDto.from(imageCard.getImage()))
			.description(imageCard.getDescription())
			.build();
	}
}
