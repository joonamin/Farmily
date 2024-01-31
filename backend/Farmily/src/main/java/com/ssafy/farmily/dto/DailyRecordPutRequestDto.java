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
@Schema(description = "일상기록 수정 요청 DTO")
public class DailyRecordPutRequestDto {
	@Schema(description = "수정할 기록의 ID")
	@NotNull
	private Long recordId;

	@Schema(description = "수정된 제목")
	@NotBlank
	private String title;

	@Schema(description = "수정된 내용")
	@NotBlank
	private String content;
}
