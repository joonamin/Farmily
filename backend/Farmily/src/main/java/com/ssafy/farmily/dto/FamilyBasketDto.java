package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.entity.type.DateRange;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FamilyBasketDto {
	private Long id;
	private DateRange range;

	public FamilyBasketDto of(Sprint sprint) {
		FamilyBasketDto familyBasketDTO = new FamilyBasketDto();
		familyBasketDTO.setId(sprint.getId());
		familyBasketDTO.setRange(sprint.getDateRange());
		return familyBasketDTO;
	}
}
