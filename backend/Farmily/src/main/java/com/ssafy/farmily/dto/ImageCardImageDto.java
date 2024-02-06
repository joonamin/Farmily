package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Schema(description = "이미지카드의 이미지 DTO")
public class ImageCardImageDto extends ImageDto {
	private Long recordId;

	public ImageCardImageDto(String location, String originalFileName, Long recordId) {
		super(location, originalFileName);
		this.recordId = recordId;
	}

	public static ImageCardImageDto of(String location, String originalFileName, Long recordId) {
		return new ImageCardImageDto(location, originalFileName, recordId);
	}
}
