package com.ssafy.farmily.dto;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.type.RecordType;

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
public class RecordBriefResponseDto {
	protected RecordType type;
	protected Long id;
	protected String title;
	protected MemberInfoDto author;
	protected LocalDateTime createdAt;

	public static RecordBriefResponseDto from(Record entity) {
		RecordBriefResponseDto dto = new RecordBriefResponseDto();
		BeanUtils.copyProperties(entity, dto);

		MemberInfoDto authorDto = MemberInfoDto.from(entity.getAuthor());
		dto.setAuthor(authorDto);

		return dto;
	}
}
