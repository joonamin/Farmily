package com.ssafy.farmily.dto;

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
			private String content;
		}

		@Getter
		@Setter
		@RequiredArgsConstructor
		@AllArgsConstructor
		@Builder
		public static class Put {
			private String content;
		}
	}

}
