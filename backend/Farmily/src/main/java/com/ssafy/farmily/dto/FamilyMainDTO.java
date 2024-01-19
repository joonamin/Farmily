package com.ssafy.farmily.dto;

import java.util.List;

import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.Tree;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FamilyMainDTO {
	Long id;
	String name;
	String motto;
	Tree tree;
	List<Long> challengesIds;

	public static FamilyMainDTO of(Family family) {
		FamilyMainDTO familyMainDTO = new FamilyMainDTO();
		familyMainDTO.setId(family.getId());
		familyMainDTO.setTree(family.getTree());
		familyMainDTO.setName(family.getName());
		familyMainDTO.setMotto(family.getMotto());
		return familyMainDTO;
	}

}
