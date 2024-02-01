package com.ssafy.farmily.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.type.RecordType;

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
@Schema(description = "이벤트기록 응답 DTO")
public class EventRecordResponseDto extends RecordResponseDto {
	protected List<ImageCardResponseDto> imageCards;

	public static EventRecordResponseDto from(Record entity) {
		EventRecordResponseDto dto = new EventRecordResponseDto();

		BeanUtils.copyProperties(entity, dto);

		MemberInfoDto authorDto = MemberInfoDto.from(entity.getAuthor());
		dto.setAuthor(authorDto);

		List<RecordCommentDto.Response> commentDtos = entity.getComments().stream()
			.map(RecordCommentDto.Response::from)
			.toList();
		dto.setComments(commentDtos);

		List<ImageCardResponseDto> imageCardDtos = entity.getImageCards().stream()
			.map(ImageCardResponseDto::from)
			.toList();

		dto.setImageCards(imageCardDtos);
		return dto;
	}
}
