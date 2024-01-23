package com.ssafy.farmily.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.ssafy.farmily.entity.Image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "이미지 DTO")
public class ImageDto {
	private String location;
	private String originalFileName;


	public static ImageDto from(Image image) {
		Assert.notNull(image, "이미지는 null값이 될 수 없습니다.");
		ImageDto imageDto = new ImageDto();
		BeanUtils.copyProperties(image, imageDto);
		return imageDto;
	}

	public static ImageDto of(String location, String originalFileName) {
		return new ImageDto(location, originalFileName);
	}
}
