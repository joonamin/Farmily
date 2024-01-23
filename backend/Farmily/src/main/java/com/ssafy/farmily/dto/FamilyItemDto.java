package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.entity.type.Item;
import com.ssafy.farmily.entity.type.ItemType;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FamilyItemDto {
	Long id;
	Item itemCode;
	ItemType type;

	public FamilyItemDto of(FamilyItem familyItem) {
		FamilyItemDto familyItemDTO = new FamilyItemDto();
		familyItemDTO.setId(familyItem.getId());
		familyItemDTO.setItemCode(familyItem.getCode());
		familyItemDTO.setType(familyItem.getType());
		return familyItemDTO;
	}
}
