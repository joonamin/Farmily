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
	private List<MainAccessoryFruitDto> mainAccessoryFruitDtoList;

	public static FamilyMainTreeDto from(Tree tree) {
		FamilyMainTreeDto familyMainTreeDTO = new FamilyMainTreeDto();

		List<Placement> placementList = tree.getPlacements();
		List<MainAccessoryFruitDto> accessoryPlacementList = new LinkedList<>();
		List<MainRecordFruitDto> recordPlacementList = new LinkedList<>();
		for (Placement placement : placementList) {
			if (placement instanceof AccessoryPlacement) {
				accessoryPlacementList.add(MainAccessoryFruitDto.of((AccessoryPlacement)placement));
			} else if (placement instanceof FruitPlacement) {
				recordPlacementList.add(MainRecordFruitDto.of((FruitPlacement)placement));

			}
		}
		familyMainTreeDTO.setId(tree.getId());
		familyMainTreeDTO.setMainRecordFruitDtoList(recordPlacementList);
		familyMainTreeDTO.setMainAccessoryFruitDtoList(accessoryPlacementList);
		return familyMainTreeDTO;
	}
}
