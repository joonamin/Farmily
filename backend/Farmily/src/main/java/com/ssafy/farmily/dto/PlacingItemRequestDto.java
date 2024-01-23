package com.ssafy.farmily.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PlacingItemRequestDto {
	private Long memberId;
	private Long treeId;
	private List<PlacementDto> placementDtoList;		// 여기서 아이템 배치할 리스트를 받아오는데요 PlacementDto로 받아오잖아요
	// 그래서 안으로 더 가볼게요

}
