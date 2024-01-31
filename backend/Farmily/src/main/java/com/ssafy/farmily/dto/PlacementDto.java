package com.ssafy.farmily.dto;

import com.ssafy.farmily.type.AccessoryType;
import com.ssafy.farmily.utils.Position;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PlacementDto {
	private Position position;
	private String dtype;
	private Long recordId;
	private AccessoryType type;
}
