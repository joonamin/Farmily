package com.ssafy.farmily.service.family;

import java.util.List;

import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.exception.NoSuchContentException;

public interface FamilyService {

	/**
	 *
	 * @param familyId
	 * @return familyId에 해당하는 FamilyMain을 반환
	 * @throws NoSuchContentException
	 */
	public FamilyMainDto setMainFamilyInfo(Long familyId) throws NoSuchContentException;

	/**
	 *
	 * @param familyId
	 * @return familyId에 해당하는 인벤토리를 반환한다.
	 * @throws NoSuchContentException
	 */
	public List<FamilyItemDto> getFamilyInventory(Long familyId) throws NoSuchContentException;

	/**
	 *
	 * @param familyId
	 * @return familyId에 해당하는 sprintList를 반환
	 * @throws NoSuchContentException
	 */
	public List<FamilyBasketDto> getFamilySprintList(Long familyId) throws NoSuchContentException;

	/**
	 *
	 * @param placingItemRequestDto
	 * @return 나무Id의 모든 placement를 제거하고 Dto의 placementList 정보를 DB에 저장
	 * @throws NoSuchContentException
	 */
	public String placingItems(PlacingItemRequestDto placingItemRequestDto)
		throws NoSuchContentException;
}
