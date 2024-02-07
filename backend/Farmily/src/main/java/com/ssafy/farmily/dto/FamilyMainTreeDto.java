package com.ssafy.farmily.dto;

import java.util.LinkedList;
import java.util.List;

import com.ssafy.farmily.entity.AccessoryPlacement;
import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.Placement;
import com.ssafy.farmily.entity.Tree;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FamilyMainTreeDto {
	private Long id;
	private List<MainRecordFruitDto> mainRecordFruitDtoList;

	public static FamilyMainTreeDto from(Tree tree) {
		FamilyMainTreeDto familyMainTreeDTO = new FamilyMainTreeDto();

		List<Placement> placementList = tree.getPlacements();
		List<MainRecordFruitDto> recordPlacementList = new LinkedList<>();
		for (Placement placement : placementList) {
			recordPlacementList.add(MainRecordFruitDto.of((FruitPlacement)placement));
		}
		familyMainTreeDTO.setId(tree.getId());
		familyMainTreeDTO.setMainRecordFruitDtoList(recordPlacementList);
		return familyMainTreeDTO;
	}
}
