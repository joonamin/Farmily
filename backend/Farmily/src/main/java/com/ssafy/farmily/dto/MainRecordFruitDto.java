package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.type.RecordType;
import com.ssafy.farmily.utils.Position;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MainRecordFruitDto {
	private Long recordId;
	private String recordTitle;
	private Position position;
	private RecordType type;

	public static MainRecordFruitDto of(FruitPlacement placement) {
		MainRecordFruitDto mainRecordFruitDTO = new MainRecordFruitDto();

		mainRecordFruitDTO.setPosition(placement.getPosition());
		Record record = placement.getRecord();
		mainRecordFruitDTO.setRecordTitle(placement.getRecord().getTitle());
		mainRecordFruitDTO.setRecordId(record.getId());
		mainRecordFruitDTO.setType(record.getType());
		return mainRecordFruitDTO;
	}
}
