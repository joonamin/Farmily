package com.ssafy.farmily.dto;

import java.util.List;

import com.ssafy.farmily.entity.Family;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class
FamilyMainDto {
	Long id;
	String name;
	String motto;
	FamilyMainTreeDto tree;
	List<Long> challengesIds;

	public static FamilyMainDto of(Family family) {
		FamilyMainDto familyMainDTO = new FamilyMainDto();
		familyMainDTO.setId(family.getId());
		familyMainDTO.setName(family.getName());
		familyMainDTO.setMotto(family.getMotto());
		return familyMainDTO;
	}

}
