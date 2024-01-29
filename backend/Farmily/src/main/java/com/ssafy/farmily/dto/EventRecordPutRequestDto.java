package com.ssafy.farmily.dto;

import java.util.List;

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
@Schema(description = "이벤트기록 수정 요청 DTO")
public class EventRecordPutRequestDto {
	@NotNull
	private Long recordId;

	@NotBlank
	private String title;

	private List<ImageCardRequestDto> imageCards;
}
