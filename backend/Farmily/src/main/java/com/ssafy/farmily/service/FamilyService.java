package com.ssafy.farmily.service;

import java.util.List;

import com.ssafy.farmily.dto.FamilyItemDTO;
import com.ssafy.farmily.dto.FamilyMainDTO;

public interface FamilyService {
	public FamilyMainDTO familyMain(Long familyId);

	public List<FamilyItemDTO> familyInventory(Long familyId);
}
