package com.ssafy.farmily.service.family;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.ChangeLeaderRequestDto;
import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMemberResponseDto;
import com.ssafy.farmily.dto.JoinRequestDto;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.dto.PlacementDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.entity.AccessoryPlacement;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.entity.FamilyMembership;
import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.Image;
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
import com.ssafy.farmily.repository.ImageRepository;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.repository.PlacementRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.repository.TreeRepository;
import com.ssafy.farmily.service.file.FileService;
import com.ssafy.farmily.type.AccessoryType;
import com.ssafy.farmily.type.FamilyRole;
import com.ssafy.farmily.utils.DateRange;

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
	private final MemberRepository memberRepository;
	private final FileService fileService;

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
		if (temp.isPresent()) {
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
				TODO
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
		placementRepository.deleteAllByTreeId(treeId);
	}

	@Override
	@Transactional
	public void makeFamily(MakingFamilyRequestDto makingFamilyRequestDto, String username) {
		Member member = memberRepository.findByUsername(username).get();
		Image profileImage = null;
		if (makingFamilyRequestDto.getImage() != null) {
			profileImage = fileService.saveImage(makingFamilyRequestDto.getImage());
		}
		String invitationCode = UUID.randomUUID().toString();

		Family family = Family.builder()
			.name(makingFamilyRequestDto.getName())
			.motto(makingFamilyRequestDto.getMotto())
			.invitationCode(invitationCode)
			.image(profileImage)
			.sprints(List.of())
			.items(List.of())
			.build();

		Tree tree = Tree.builder().family(family).placements(List.of()).build();
		family.setTree(tree);

		familyRepository.save(family);
		treeRepository.save(tree);

		FamilyMembership familyMembership = FamilyMembership.builder()
			.family(family)
			.member(member)
			.role(FamilyRole.LEADER)
			.build();

		familyMembershipRepository.save(familyMembership);
	}

	@Override
	@Transactional
	public void swapSprint(Long familyId) {
		Optional<Sprint> unHarvestedSprint = sprintRepository.findByFamilyIdAndIsHarvested(familyId, false);
		if (unHarvestedSprint.isPresent()) {
			Sprint pastSprint = unHarvestedSprint.get();
			if (LocalDate.now().isBefore(pastSprint.getDateRange().getEndDate())) {
				throw new BusinessException("수확 기간을 만족하지 못했습니다.");
			}
			pastSprint.setIsHarvested(true);
			sprintRepository.save(pastSprint);
		}
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
	}

	@Override
	public void insertFamilyMemberShip(JoinRequestDto requestDto, String username) throws NoSuchContentException {
		String inviteCode = requestDto.getInvitationCode();
		Family family = familyRepository.findByInvitationCode(inviteCode)
			.orElseThrow(() -> new NoSuchContentException("존재 하지 않는 초대코드입니다."));
		Member member = memberRepository.findByUsername(username).get();
		FamilyMembership familyMembership = FamilyMembership.builder()
			.member(member)
			.family(family)
			.role(FamilyRole.MEMBER)
			.build();

		familyMembershipRepository.save(familyMembership);
	}

	@Override
	public String getInvitationCode(Long familyId) throws NoSuchContentException {
		Family family = familyRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("유효하지 않은 가족입니다."));
		return family.getInvitationCode();
	}

	// @Override
	// public Long raffleItem(Long familyId) {
	// 	Family family = familyRepository.findById(familyId)
	// 		.orElseThrow(() -> new NoSuchContentException("유효하지 않은 가족입니다."));
	//
	// 	int familyPoint = family.getPoint();
	// 	if (familyPoint >= 150) {
	// 		familyPoint -= 150;
	// 		boolean duplication = true;
	//
	// 		// TODO 아이템 DB or ENUM 어떻게 저장할 지 얘기해보고 모든 아이템 개수 가져오는 로직 만들어야 될 것 같아요
	// 		int NumOfAllItem = 10;
	// 		while (duplication) {
	// 			int rafflingItemId = (int) Math.random() * NumOfAllItem + 1;
	// 			// rafflingItemId를 통해 아이템 가져오고
	//
	// 			// duplication이 발생하지 않으면
	// 			if() {    // getFamilyInventory not contain item(rafflingItemId)
	// 				familyItemRepository.save(item);
	// 				duplication = false;
	// 			}
	// 		}
	// 	}
	//
	// 	return item.getid;
	// }
	@Override
	public List<FamilyMemberResponseDto> loadFamilyMemberList(Long familyId, String username) {
		Member me = memberRepository.findByUsername(username).get();
		List<FamilyMemberResponseDto> familyMembers = memberRepository.findAllByFamilyId(familyId);
		return checkIsMe(familyMembers, me.getId());
	}

	private List<FamilyMemberResponseDto> checkIsMe(List<FamilyMemberResponseDto> familyMembers, Long me) {
		List<FamilyMemberResponseDto> result = new ArrayList<>();
		for (FamilyMemberResponseDto dto : familyMembers) {
			dto.setMe(dto.getMemberId() == me);
			result.add(dto);
		}
		return result;
	}

	@Override
	public void changeLeader(Long familyId, ChangeLeaderRequestDto requestDto, String pastLeaderName) throws BusinessException{
		Long newLeaderId = requestDto.getNewLeaderMemberId();
		Member pastLeader = memberRepository.findByUsername(pastLeaderName).get();
		Long pastLeaderId = pastLeader.getId();
		FamilyMembership pastLeaderMemberShip = getPastLeaderMembership(familyId, pastLeaderId);
		FamilyMembership newLeaderMemberShip = familyMembershipRepository.findByFamilyIdAndMemberId(familyId,
			newLeaderId).get();

		newLeaderMemberShip.setRole(FamilyRole.LEADER);
		pastLeaderMemberShip.setRole(FamilyRole.MEMBER);

		familyMembershipRepository.save(newLeaderMemberShip);
		familyMembershipRepository.save(pastLeaderMemberShip);
	}

	private FamilyMembership getPastLeaderMembership(Long familyId, Long pastLeaderId) throws BusinessException {
		FamilyMembership pastLeaderMemberShip = familyMembershipRepository.findByFamilyIdAndMemberId(familyId,
			pastLeaderId).get();
		if (!pastLeaderMemberShip.getRole().equals(FamilyRole.LEADER)) {
			throw new BusinessException("가장이 아닙니다.");
		}
		return pastLeaderMemberShip;
	}

}
