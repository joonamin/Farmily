package com.ssafy.farmily.service.family;

import java.util.List;

import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.exception.NotFoundFamilyId;

public interface FamilyService {

	/**
	 *
	 * @param familyId
	 * @return familyId에 해당하는 FamilyMain을 반환
	 *
	 */
	public FamilyMainDto setMainFamilyInfo(Long familyId);

	/**
	 *
	 * @param familyId
	 * @return familyId에 해당하는 인벤토리를 반환한다.
	 * @throws NotFoundFamilyId
	 */
	public List<FamilyItemDto> getFamilyInventory(Long familyId) throws NotFoundFamilyId;

	/**
	 *
	 * @param familyId
	 * @return familyId에 해당하는 sprintList를 반환
	 * @throws NotFoundFamilyId
	 */
	public List<FamilyBasketDto> getFamilySprintList(Long familyId) throws NotFoundFamilyId;

	public String placingItems(PlacingItemRequestDto placingItemRequestDto);
}
