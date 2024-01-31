package com.ssafy.farmily.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PlacingItemRequestDto {
	private Long treeId;
	private List<PlacementDto> placementDtoList;
}
