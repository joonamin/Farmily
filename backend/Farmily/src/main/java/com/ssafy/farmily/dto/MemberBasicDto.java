package com.ssafy.farmily.dto;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.Member;

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
public class MemberBasicDto {
	private Long id;
	private String nickname;

	public static MemberBasicDto from(Member entity) {
		MemberBasicDto dto = new MemberBasicDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
