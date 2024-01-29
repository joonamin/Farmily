package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.AccessoryPlacement;
import com.ssafy.farmily.type.AccessoryType;
import com.ssafy.farmily.utils.Position;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MainAccessoryFruitDto {
	private Long id;
	private int row;
	private int col;
	private AccessoryType accessoryType;

	public static MainAccessoryFruitDto of(AccessoryPlacement placement) {
		MainAccessoryFruitDto mainAccessoryFruitDTO = new MainAccessoryFruitDto();
		Position position = placement.getPosition();
		mainAccessoryFruitDTO.setRow(position.getRow());
		mainAccessoryFruitDTO.setCol(position.getCol());
		mainAccessoryFruitDTO.setAccessoryType(placement.getType());
		mainAccessoryFruitDTO.setId(placement.getId());
		return mainAccessoryFruitDTO;
	}
}
