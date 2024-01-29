package com.ssafy.farmily.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponseDto {
	private List<CommunityPostDto> communityPostDtoList;
	private int pageNo;
	private int pageSize;
	private int totalElements;
	private int totalPages;
	private boolean last;
}
