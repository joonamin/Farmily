package com.ssafy.farmily.dto;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.Member;

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
@Schema(description = "사용자 기본정보 DTO")
public class MemberBasicDto {
	private Long id;
	private String nickname;

	public static MemberBasicDto from(Member entity) {
		MemberBasicDto dto = new MemberBasicDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
