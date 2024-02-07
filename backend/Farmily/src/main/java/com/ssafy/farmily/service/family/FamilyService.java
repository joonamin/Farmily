package com.ssafy.farmily.service.family;

import java.util.List;
import java.util.Map;

import com.ssafy.farmily.dto.ChangeLeaderRequestDto;
import com.ssafy.farmily.dto.CreateFamilyResponseDto;
import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyFruitSkinsDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyListDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMemberResponseDto;
import com.ssafy.farmily.dto.FamilyAchievementProgressDto;
import com.ssafy.farmily.dto.FamilyPatchRequestDto;
import com.ssafy.farmily.dto.FamilyStatisticsResponseDto;
import com.ssafy.farmily.dto.GetInventoryResponseDto;
import com.ssafy.farmily.dto.JoinRequestDto;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.dto.RafflingRequestDto;
import com.ssafy.farmily.dto.RafflingResponseDto;
import com.ssafy.farmily.dto.RefreshSprintRequestDto;
import com.ssafy.farmily.dto.ServiceProcessResult;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.type.Item;

public interface FamilyService {

	Family getEntity(Long familyId);

	void assertExists(Long familyId);

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
	public GetInventoryResponseDto getFamilyInventory(String username,Long familyId,Long sprintId);

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
	public ServiceProcessResult swapSprint(RefreshSprintRequestDto requestDto);

	public void insertFamilyMemberShip(JoinRequestDto requestDto,String username);

	public String getInvitationCode(Long familyId);

	public List<FamilyMemberResponseDto> loadFamilyMemberList(Long familyId,String username);

	public void changeLeader(Long familyId, ChangeLeaderRequestDto requestDto, String pastLeaderName);

	public FamilyListDto getFamilyList(String username);

	public RafflingResponseDto raffleItem(RafflingRequestDto dto,String username);

	void editFruitSkin(String username, Long familyId, FamilyFruitSkinsDto dto);

	void changeName(String username, Long familyId, FamilyPatchRequestDto.Name dto);

	void changeMotto(String username, Long familyId, FamilyPatchRequestDto.Motto dto);

	void changeImage(String username, Long familyId, FamilyPatchRequestDto.Image dto);
}
