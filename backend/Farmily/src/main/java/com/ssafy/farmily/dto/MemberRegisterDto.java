package com.ssafy.farmily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterDto {
	private String username;
	private String password;
	private String nickname;
	private ImageDto profilePic;
}
