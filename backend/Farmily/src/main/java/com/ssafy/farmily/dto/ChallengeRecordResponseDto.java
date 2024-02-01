package com.ssafy.farmily.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.ChallengeProgress;
import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.utils.DateRange;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Schema(description = "챌린지기록 응답 DTO")
public class ChallengeRecordResponseDto extends RecordResponseDto {
	protected DateRange dateRange;
	protected Boolean isRewarded;
	protected List<LocalDate> progresses;

	public static ChallengeRecordResponseDto from(ChallengeRecord entity) {
		ChallengeRecordResponseDto dto = new ChallengeRecordResponseDto();

		BeanUtils.copyProperties(entity, dto);

		MemberInfoDto authorDto = MemberInfoDto.from(entity.getAuthor());
		dto.setAuthor(authorDto);

		List<RecordCommentDto.Response> commentDtos = entity.getComments().stream()
			.map(RecordCommentDto.Response::from)
			.toList();
		dto.setComments(commentDtos);

		List<LocalDate> progressDtos = entity.getProgresses().stream()
			.map(ChallengeProgress::getDate)
			.toList();

		dto.setProgresses(progressDtos);

		return dto;
	}
}
