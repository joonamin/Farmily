package com.ssafy.farmily.dto;

import java.util.List;

import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.Tree;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FamilyMainDto {
	private Long id;
	private String name;
	private String motto;
	private FamilyMainTreeDto tree;
	private List<Long> challengesIds;
	private MainSprintResponseDto mainSprint;
	private String invitationCode;
	private FamilyFruitSkinsDto fruitSkins;
	public static FamilyMainDto of(Family family) {
		FamilyMainDto familyMainDTO = new FamilyMainDto();
		familyMainDTO.setId(family.getId());
		familyMainDTO.setName(family.getName());
		familyMainDTO.setMotto(family.getMotto());
		familyMainDTO.setInvitationCode(family.getInvitationCode());
		Tree tree = family.getTree();
		familyMainDTO.setTree(FamilyMainTreeDto.from(tree));

		familyMainDTO.setFruitSkins(FamilyFruitSkinsDto.from(family.getFruitSkins()));

		return familyMainDTO;
	}

}
