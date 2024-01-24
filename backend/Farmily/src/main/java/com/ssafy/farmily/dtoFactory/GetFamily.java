package com.ssafy.farmily.dtoFactory;

import java.util.LinkedList;
import java.util.List;

import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMainTreeDto;
import com.ssafy.farmily.dto.MainAccessoryFruitDto;
import com.ssafy.farmily.dto.MainRecordFruitDto;
import com.ssafy.farmily.entity.AccessoryPlacement;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.Placement;
import com.ssafy.farmily.entity.Tree;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetFamily {
	public static FamilyMainDto familyToDTO(Family family) {
		FamilyMainDto familyMainDTO = FamilyMainDto.of(family);
		Tree tree = family.getTree();
		familyMainDTO.setTree(treeToDTO(tree));
		return familyMainDTO;
	}

	public static FamilyMainTreeDto treeToDTO(Tree tree) {
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
