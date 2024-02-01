package com.ssafy.farmily.dto;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.Comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class RecordCommentDto {

	public static class Request {
		@Getter
		@Setter
		@RequiredArgsConstructor
		@AllArgsConstructor
		@Builder
		public static class Post {
			@NotNull
			private String content;
		}

		@Getter
		@Setter
		@RequiredArgsConstructor
		@AllArgsConstructor
		@Builder
		public static class Put {
			@NotNull
			private String content;
		}
	}

	@RequiredArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	@Builder
	@Schema(description = "댓글 DTO")
	public static class Response {
		private Long id;
		private String content;
		private MemberInfoDto author;

		public static Response from(Comment entity) {
			Response dto = new Response();
			BeanUtils.copyProperties(entity, dto);
			dto.setAuthor(MemberInfoDto.from(entity.getAuthor()));
			return dto;
		}
	}

}
