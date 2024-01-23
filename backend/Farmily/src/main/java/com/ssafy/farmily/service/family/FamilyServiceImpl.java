package com.ssafy.farmily.service.family;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.entity.Tree;
import com.ssafy.farmily.entity.type.AccessoryType;
import com.ssafy.farmily.exception.NotFoundFamilyId;
import com.ssafy.farmily.repository.FamilyItemRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.PlacementRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.repository.TreeRepository;

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

	@Override
	public FamilyMainDto setMainFamilyInfo(Long familyId) throws NotFoundFamilyId {
		Optional<Family> temp = familyRepository.findById(familyId);
		FamilyMainDto familyMainDTO = null;
		if (temp.isPresent()) {
			Family family = temp.get();
			familyMainDTO = FamilyMainDto.of(family);
			new FamilyMainTreeDto();
			Optional<Tree> temp3 = treeRepository.findById(familyId);
			Tree tree = temp3.get();
			FamilyMainTreeDto familyMainTreeDTO = GetFamily.treeToDTO(tree);
			List<Long> temp2 = recordRepository.findCurrentChallenges(familyId);
			familyMainDTO.setChallengesIds(temp2);
			familyMainDTO.setTree(familyMainTreeDTO);
		} else {
			throw new NotFoundFamilyId();
		}
		return familyMainDTO;
	}

	@Override
	public List<FamilyItemDto> getFamilyInventory(Long familyId) throws NotFoundFamilyId {
		Optional<Family> temp = familyRepository.findById(familyId);

		// 받아올 때 추가가 많이 발생할 것 같아서 LinkedList
		List<FamilyItemDto> familyItemDtoList = new LinkedList<>();
		if (temp.isPresent()) {
			List<FamilyItem> temp2 = familyItemRepository.findByFamilyId(familyId);

			for (FamilyItem item : temp2) {
				FamilyItemDto familyItemDTO = new FamilyItemDto().of(item);
				familyItemDtoList.add(familyItemDTO);
			}
		} else {
			throw new NotFoundFamilyId();
		}

		return familyItemDtoList;
	}

	@Override
	public List<FamilyBasketDto> getFamilySprintList(Long familyId) {
		Optional<Family> temp = familyRepository.findById(familyId);
		List<FamilyBasketDto> familySprintList = new ArrayList<>();
		if (temp.isPresent()) {
			List<Sprint> temp2 = sprintRepository.findByFamilyIdAndIsHarvested(familyId,true);
			for (Sprint sprint : temp2) {
				FamilyBasketDto familyBasketDTO = new FamilyBasketDto().of(sprint);
				familySprintList.add(familyBasketDTO);
			}
		} else {
			throw new NotFoundFamilyId();
		}
		return familySprintList;
	}

	@Override
	public String placingItems(PlacingItemRequestDto placingItemRequestDto) {
		Long treeId = placingItemRequestDto.getTreeId();
		Tree tree = treeRepository.findById(treeId).get();
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
				Record record = recordRepository.findById(placementDto.getRecordId()).get();
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