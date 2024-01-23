package com.ssafy.farmily.dto;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDto {
	private String username;
	private String nickname;
	private ImageDto profilePic;

	public static MemberInfoDto from(Member member) {
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		BeanUtils.copyProperties(member, memberInfoDto, "profilePic");
		memberInfoDto.setProfilePic(ImageDto.from(member.getProfilePic()));
		return memberInfoDto;
	}
}
