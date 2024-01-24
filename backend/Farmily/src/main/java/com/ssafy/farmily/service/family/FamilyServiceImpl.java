package com.ssafy.farmily.service.family;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMainTreeDto;
import com.ssafy.farmily.dto.PlacementDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.dtoFactory.GetFamily;
import com.ssafy.farmily.entity.AccessoryPlacement;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.entity.FamilyMembership;
import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.entity.Tree;
import com.ssafy.farmily.entity.type.AccessoryType;
import com.ssafy.farmily.exception.NotFoundFamilyId;
import com.ssafy.farmily.exception.NotFoundRecordId;
import com.ssafy.farmily.exception.NotFoundTreeId;
import com.ssafy.farmily.exception.PermissionException;
import com.ssafy.farmily.repository.FamilyItemRepository;
import com.ssafy.farmily.repository.FamilyMembershipRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.PlacementRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.repository.TreeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FamilyServiceImpl implements FamilyService {
	private final FamilyRepository familyRepository;
	private final RecordRepository recordRepository;
	private final FamilyItemRepository familyItemRepository;
	private final SprintRepository sprintRepository;
	private final PlacementRepository placementRepository;
	private final TreeRepository treeRepository;
	private final FamilyMembershipRepository familyMembershipRepository;

	@Override
	public FamilyMainDto setMainFamilyInfo(Long familyId) throws NotFoundFamilyId {
		Family family = (Family)familyRepository.findById(familyId).orElseThrow(NotFoundFamilyId::new);
		Tree tree = (Tree)treeRepository.findById(familyId).orElseThrow(NotFoundTreeId::new);
		List<Long> temp = recordRepository.findCurrentChallenges(familyId);

		FamilyMainDto familyMainDTO = null;
		familyMainDTO = FamilyMainDto.of(family);
		FamilyMainTreeDto familyMainTreeDTO = GetFamily.treeToDTO(tree);
		familyMainDTO.setChallengesIds(temp);
		familyMainDTO.setTree(familyMainTreeDTO);
		return familyMainDTO;
	}

	@Override
	public List<FamilyItemDto> getFamilyInventory(Long familyId, Long memberId) throws
		NotFoundFamilyId,
		PermissionException {
		Family family = (Family)familyRepository.findById(familyId).orElseThrow(NotFoundFamilyId::new);
		FamilyMembership authorization =
			familyMembershipRepository.findByFamilyIdAndMemberId(familyId, memberId)
				.orElseThrow(PermissionException::new);
		// 받아올 때 추가가 많이 발생할 것 같아서 LinkedList
		List<FamilyItemDto> familyItemDtoList = new LinkedList<>();
		List<FamilyItem> temp = familyItemRepository.findByFamilyId(familyId);

		for (FamilyItem item : temp) {
			FamilyItemDto familyItemDTO = new FamilyItemDto().of(item);
			familyItemDtoList.add(familyItemDTO);
		}

		return familyItemDtoList;
	}

	@Override
	public List<FamilyBasketDto> getFamilySprintList(Long familyId, Long memberId) throws NotFoundFamilyId,PermissionException {
		Family family = familyRepository.findById(familyId).orElseThrow(NotFoundFamilyId::new);
		FamilyMembership authorization =
			familyMembershipRepository.findByFamilyIdAndMemberId(familyId, memberId)
				.orElseThrow(PermissionException::new);
		List<FamilyBasketDto> familySprintList = new ArrayList<>();
		List<Sprint> temp = sprintRepository.findByFamilyIdAndIsHarvested(familyId, true);
		for (Sprint sprint : temp) {
			FamilyBasketDto familyBasketDTO = new FamilyBasketDto().of(sprint);
			familySprintList.add(familyBasketDTO);
		}

		return familySprintList;
	}

	@Transactional
	@Override
	public String placingItems(PlacingItemRequestDto placingItemRequestDto) throws NotFoundTreeId, NotFoundRecordId {
		Long treeId = placingItemRequestDto.getTreeId();
		Tree tree = treeRepository.findById(treeId).orElseThrow(NotFoundTreeId::new);
		FamilyMembership authorization =
			familyMembershipRepository.findByFamilyIdAndMemberId(treeId, placingItemRequestDto.getMemberId())
				.orElseThrow(PermissionException::new);
		placementRepository.deleteAllByTreeId(treeId);
		for (PlacementDto placementDto : placingItemRequestDto.getPlacementDtoList()) {
				/*
				해당 아이템이 인벤토리에 존재하는 지 확인하는 로직이 필요할 거 같은데
				아직 placement에 accessory에 대한 고유 Id가 없어서 구현 불가
				 */
			if (placementDto.getDtype().equals("A")) {
				AccessoryPlacement accessoryPlacement = AccessoryPlacement.builder()
					.position(placementDto.getPosition())
					.tree(tree)
					.type(AccessoryType.HIDDEN_FRUIT)
					.build();
				placementRepository.save(accessoryPlacement);
			} else if (placementDto.getDtype().equals("F")) {
				recordRepository.findById(placementDto.getRecordId()).orElseThrow(NotFoundRecordId::new);
				Record record = (Record)recordRepository.findById(placementDto.getRecordId())
					.orElseThrow(NotFoundRecordId::new);
				FruitPlacement fruitPlacement = FruitPlacement.builder()
					.position(placementDto.getPosition())
					.tree(tree)
					.record(record)
					.build();
				placementRepository.save(fruitPlacement);
			}
		}
		return "save success";
	}
}