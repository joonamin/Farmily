package com.ssafy.farmily.dto;

import com.ssafy.farmily.type.FamilyRole;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FamilyMemberResponseDto {
	private Long memberId;
	private String nickname;
	private FamilyRole role;
	private boolean isMe;

	public FamilyMemberResponseDto(Long memberId, String nickname, FamilyRole role) {
		this.memberId = memberId;
		this.nickname = nickname;
		this.role = role;
	}
}
