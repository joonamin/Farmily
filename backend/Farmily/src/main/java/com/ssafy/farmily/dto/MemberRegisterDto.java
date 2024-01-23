package com.ssafy.farmily.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "사용자 회원가입 요청 DTO")
public class MemberRegisterDto {
	private String username;
	private String password;
	private String nickname;
	private ImageDto profilePic;
}
