package com.ssafy.farmily.dto;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.annotation.NotInverted;
import com.ssafy.farmily.validation.annotation.StartsNowOrLater;

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
@Schema(description = "챌린지기록 작성 요청 DTO")
public class ChallengeRecordPostRequestDto {
	@NotNull
	private Long familyId;

	@NotNull
	private Long sprintId;

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	@NotInverted
	@StartsNowOrLater
	private DateRange dateRange;
}
