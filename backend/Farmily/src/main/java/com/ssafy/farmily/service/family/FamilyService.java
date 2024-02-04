package com.ssafy.farmily.service.family;

import java.util.List;

import com.ssafy.farmily.dto.ChangeLeaderRequestDto;
import com.ssafy.farmily.dto.CreateFamilyResponseDto;
import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyListDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMemberResponseDto;
import com.ssafy.farmily.dto.FamilyAchievementProgressDto;
import com.ssafy.farmily.dto.FamilyStatisticsResponseDto;
import com.ssafy.farmily.dto.JoinRequestDto;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.dto.RafflingRequestDto;
import com.ssafy.farmily.dto.RafflingResponseDto;
import com.ssafy.farmily.dto.RefreshSprintRequestDto;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.type.Item;

public interface FamilyService {

	/**
	 *
	 * @param familyId
	 * @return familyId에 해당하는 FamilyMain을 반환
	 * @throws NoSuchContentException
	 */
	public FamilyMainDto setMainFamilyInfo(Long familyId);

	/**
	 *
	 * @param familyId
	 * @return familyId에 해당하는 인벤토리를 반환한다.
	 * @throws NoSuchContentException
	 */
	public List<FamilyItemDto> getFamilyInventory(Long familyId);

	/**
	 *
	 * @param familyId
	 * @return familyId에 해당하는 sprintList를 반환
	 * @throws NoSuchContentException
	 */
	public List<FamilyBasketDto> getFamilySprintList(Long familyId);

	void assertMembership(Long familyId, String username);

	/**
	 *
	 * @param placingItemRequestDto
	 * @return 나무Id의 모든 placement를 제거하고 Dto의 placementList 정보를 DB에 저장
	 * @throws NoSuchContentException
	 */
	public void placingItems(PlacingItemRequestDto placingItemRequestDto);

	public void deletePlacement(Long treeId);

	public CreateFamilyResponseDto makeFamily(MakingFamilyRequestDto makingFamilyRequestDto, String username);
	public void swapSprint(RefreshSprintRequestDto requestDto);

	public void insertFamilyMemberShip(JoinRequestDto requestDto,String username);

	public String getInvitationCode(Long familyId);

	public List<FamilyMemberResponseDto> loadFamilyMemberList(Long familyId,String username);

	public void changeLeader(Long familyId, ChangeLeaderRequestDto requestDto, String pastLeaderName);

	public FamilyListDto getFamilyList(String username);

	public RafflingResponseDto raffleItem(RafflingRequestDto dto,String username);

	public List<FamilyStatisticsResponseDto> familyAchievementProgress(Long familyId);
}
