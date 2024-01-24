package com.ssafy.farmily.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FamilyMainTreeDto {
	private Long id;
	private List<MainRecordFruitDto> mainRecordFruitDtoList;
	private List<MainAccessoryFruitDto> mainAccessoryFruitDtoList;
}
