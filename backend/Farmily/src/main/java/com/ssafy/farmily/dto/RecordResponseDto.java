package com.ssafy.farmily.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.type.RecordType;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "기록 응답 DTO")
public class RecordResponseDto {
	protected RecordType recordType;
	protected Long id;
	protected String title;
	protected MemberBasicDto author;
	protected List<CommentDto> comments;
	protected LocalDateTime createdAt;
	protected LocalDateTime lastEditedAt;

	public static RecordResponseDto from(Record entity) {
		RecordResponseDto dto = new RecordResponseDto();
		BeanUtils.copyProperties(entity, dto);

		MemberBasicDto authorDto = MemberBasicDto.from(entity.getAuthor());
		dto.setAuthor(authorDto);

		List<CommentDto> commentDtos = entity.getComments().stream()
			.map(CommentDto::from)
			.toList();
		dto.setComments(commentDtos);

		return dto;
	}
}
