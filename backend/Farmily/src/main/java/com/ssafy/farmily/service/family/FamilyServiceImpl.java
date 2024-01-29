package com.ssafy.farmily.service.family;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.dto.PlacementDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.entity.AccessoryPlacement;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.entity.FamilyMembership;
import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Placement;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.entity.Tree;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.FamilyItemRepository;
import com.ssafy.farmily.repository.FamilyMembershipRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.PlacementRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.repository.TreeRepository;
import com.ssafy.farmily.type.AccessoryType;
import com.ssafy.farmily.type.FamilyRole;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.ssafy.farmily.utils.DateRange;

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
	@Transactional
	public FamilyMainDto setMainFamilyInfo(Long familyId) {
		Family family = (Family)familyRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("존재하지 않는 가족입니다."));
		Tree tree = (Tree)treeRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("존재하지 않는 나무입니다."));
		List<Placement> placementList = placementRepository.findAllByTreeId(tree.getId());
		tree.setPlacements(placementList);
		family.setTree(tree);
		List<Long> challenges = recordRepository.findCurrentChallenges(familyId);
		Optional<Sprint> temp = sprintRepository.findByFamilyIdAndIsHarvested(familyId, false);

		FamilyMainDto familyMainDTO = FamilyMainDto.of(family);
		familyMainDTO.setChallengesIds(challenges);
		if(temp.isPresent()) {
			Sprint sprint = temp.get();
			familyMainDTO.setSprintId(sprint.getId());
		}
		return familyMainDTO;
	}

	@Override
	@Transactional
	public List<FamilyItemDto> getFamilyInventory(Long familyId) {
		Family family = (Family)familyRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("존재하지 않는 가족입니다."));
		// 받아올 때 추가가 많이 발생할 것 같아서 LinkedList
		List<FamilyItemDto> familyItemDtoList = new LinkedList<>();
		List<FamilyItem> temp = familyItemRepository.findByFamilyId(familyId);
		for (FamilyItem item : temp) {
			FamilyItemDto familyItemDTO = FamilyItemDto.of(item);
			familyItemDtoList.add(familyItemDTO);
		}
		return familyItemDtoList;
	}

	@Override
	@Transactional
	public List<FamilyBasketDto> getFamilySprintList(Long familyId) {
		Family family = familyRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("존재하지 않는 가족입니다."));
		List<FamilyBasketDto> familySprintList = new ArrayList<>();
		List<Sprint> temp = sprintRepository.findAllByFamilyIdAndIsHarvested(familyId, true);
		for (Sprint sprint : temp) {
			FamilyBasketDto familyBasketDTO = new FamilyBasketDto().of(sprint);
			familySprintList.add(familyBasketDTO);
		}

		return familySprintList;
	}

	@Transactional
	@Override
	public void placingItems(PlacingItemRequestDto placingItemRequestDto) {
		Long treeId = placingItemRequestDto.getTreeId();
		Tree tree = treeRepository.findById(treeId).orElseThrow(() -> new NoSuchContentException("잘못 된 트리입니다."));
		deletePlacement(treeId);
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
				Record record = (Record)recordRepository.findById(placementDto.getRecordId())
					.orElseThrow(() -> new NoSuchContentException("존재하지 않는 글입니다."));
				FruitPlacement fruitPlacement = FruitPlacement.builder()
					.position(placementDto.getPosition())
					.tree(tree)
					.record(record)
					.build();
				placementRepository.save(fruitPlacement);
			}
		}
	}

	@Override
	@Transactional
	public void deletePlacement(Long treeId) {
		placementRepository.deleteByTreeId(treeId);
	}

	@Override
	@Transactional
	public void makeFamily(MakingFamilyRequestDto makingFamilyRequestDto) {
		String invitationCode = RandomStringUtils.randomAlphanumeric(32);

		Family family = Family.builder()
			.name(makingFamilyRequestDto.getName())
			.motto(makingFamilyRequestDto.getMotto())
			.invitationCode(invitationCode)
			.sprints(List.of())
			.items(List.of())
			.build();

		Tree tree = Tree.builder().family(family).placements(new LinkedList<>()).build();
		family.setTree(tree);

		familyRepository.save(family);
		treeRepository.save(tree);

		Member member = makingFamilyRequestDto.getMember();

		FamilyMembership familyMembership = FamilyMembership.builder()
			.family(family)
			.member(member)
			.role(FamilyRole.LEADER)
			.build();

		List<FamilyMembership> memberships = member.getFamilyMemberships();
		memberships.add(familyMembership);
		member.setFamilyMemberships(memberships);

		familyMembershipRepository.save(familyMembership);
	}

	@Override
	@Transactional
	public void swapSprint(Long familyId){
		Optional<Sprint> unHarvestedSprint = sprintRepository.findByFamilyIdAndIsHarvested(familyId,false);
		if(unHarvestedSprint.isPresent()) {
			Sprint pastSprint = unHarvestedSprint.get();
			if(LocalDate.now().isBefore(pastSprint.getDateRange().getEndDate())){
				throw new BusinessException("수확 기간을 만족하지 못했습니다.");
			}
			pastSprint.setIsHarvested(true);
			sprintRepository.save(pastSprint);
		}
		// 여기 부터
		LocalDate startDate = LocalDate.now();
		YearMonth yearMonth = YearMonth.from(startDate);
		LocalDate endDate = yearMonth.atEndOfMonth();

		DateRange dateRange = new DateRange();
		dateRange.setStartDate(startDate);
		dateRange.setEndDate(endDate);

		Sprint sprint = Sprint.builder()
			.isHarvested(false)
			.dateRange(dateRange)
			.family(familyRepository.findById(familyId).get())
			.build();

		sprintRepository.save(sprint);
		// 여기 까지 >>> sprintService.create(family);
	}

	// Todo : 가족 생성, 스프린트 삭제 후 생성 (수확 후 다시 키우기),
}
