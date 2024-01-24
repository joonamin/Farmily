package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.type.Position;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MainRecordFruitDto {
	private Long recordId;
	private String recordTitle;
	private int row;
	private int col;

	public static MainRecordFruitDto of(FruitPlacement placement) {
		MainRecordFruitDto mainRecordFruitDTO = new MainRecordFruitDto();
		Position position = placement.getPosition();
		mainRecordFruitDTO.setRow(position.getRow());
		mainRecordFruitDTO.setCol(position.getCol());
		mainRecordFruitDTO.setRecordId(placement.getRecord().getId());
		mainRecordFruitDTO.setRecordTitle(placement.getRecord().getTitle());
		return mainRecordFruitDTO;
	}
}
