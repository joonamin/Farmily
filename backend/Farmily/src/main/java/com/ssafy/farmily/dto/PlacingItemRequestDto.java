package com.ssafy.farmily.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PlacingItemRequestDto {
	@NotNull
	private Long treeId;

	private List<PlacementDto> placementDtoList;
}
