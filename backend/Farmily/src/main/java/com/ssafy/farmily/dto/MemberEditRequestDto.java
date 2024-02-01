package com.ssafy.farmily.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "사용자 정보 수정 요청 DTO")
public class MemberEditRequestDto {
	@NotNull
	private String newNickname;
}
