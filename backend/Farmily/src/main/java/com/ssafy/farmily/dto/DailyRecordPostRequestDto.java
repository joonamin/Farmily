package com.ssafy.farmily.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "일상기록 작성 요청 DTO")
public class DailyRecordPostRequestDto {
	@NotNull
	private Long familyId;

	@Schema(description = "기록이 속할 스프린트의 ID")
	@NotNull
	private Long sprintId;

	@Schema(description = "제목")
	@NotBlank
	private String title;

	@Schema(description = "내용")
	@NotBlank
	private String content;
}
