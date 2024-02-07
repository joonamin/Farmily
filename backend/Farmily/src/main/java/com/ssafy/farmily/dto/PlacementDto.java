package com.ssafy.farmily.dto;

import com.ssafy.farmily.type.AccessoryType;
import com.ssafy.farmily.utils.Position;
import com.ssafy.farmily.validation.annotation.InRectangle;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PlacementDto {
	@InRectangle(minCol = 0, maxCol = 99, minRow = 0, maxRow = 99)
	private Position position;

	private Long recordId;


}
