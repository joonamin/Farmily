package com.ssafy.farmily.service.family;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.validator.routines.DomainValidator;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.ChangeLeaderRequestDto;
import com.ssafy.farmily.dto.CreateFamilyResponseDto;
import com.ssafy.farmily.dto.FamilyAchievementProgressDto;
import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyListDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMemberResponseDto;
import com.ssafy.farmily.dto.FamilyStatisticsResponseDto;
import com.ssafy.farmily.dto.JoinRequestDto;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.dto.PlacementDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.dto.RafflingRequestDto;
import com.ssafy.farmily.dto.RafflingResponseDto;
import com.ssafy.farmily.dto.RefreshSprintRequestDto;
import com.ssafy.farmily.entity.AccessoryPlacement;
import com.ssafy.farmily.entity.AchievementRewardHistory;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.entity.FamilyMembership;
import com.ssafy.farmily.entity.FamilyStatistics;
import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Placement;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.entity.Tree;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.exception.ForbiddenException;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.AchievementRewardHistoryRepository;
import com.ssafy.farmily.repository.FamilyItemRepository;
import com.ssafy.farmily.repository.FamilyMembershipRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.FamilyStatisticsRepository;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.repository.PlacementRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.repository.TreeRepository;
import com.ssafy.farmily.service.file.FileService;
import com.ssafy.farmily.service.member.MemberService;
import com.ssafy.farmily.type.AccessoryType;
import com.ssafy.farmily.type.Achievement;
import com.ssafy.farmily.type.FamilyRole;
import com.ssafy.farmily.type.Item;
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
	private final MemberRepository memberRepository;
	private final FamilyMembershipRepository familyMembershipRepository;
	private final FamilyStatisticsRepository familyStatisticsRepository;
	private final AchievementRewardHistoryRepository achievementRewardHistoryRepository;

	private final MemberService memberService;
	private final FileService fileService;
	private final int RAFFLING_COST = 100;

	@Override
	@Transactional
	public FamilyMainDto setMainFamilyInfo(Long familyId) {
		Family family = familyRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("존재하지 않는 가족입니다."));
		Tree tree = treeRepository.findById(familyId).orElseThrow(() -> new NoSuchContentException("존재하지 않는 나무입니다."));
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
		familyRepository.findById(familyId).orElseThrow(() -> new NoSuchContentException("존재하지 않는 가족입니다."));

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
		familyRepository.findById(familyId).orElseThrow(() -> new NoSuchContentException("존재하지 않는 가족입니다."));

		List<FamilyBasketDto> familySprintList = new ArrayList<>();
		List<Sprint> temp = sprintRepository.findAllByFamilyIdAndIsHarvestedOrderByIdDesc(familyId, true);
		for (Sprint sprint : temp) {
			FamilyBasketDto familyBasketDTO = new FamilyBasketDto().of(sprint);
			familySprintList.add(familyBasketDTO);
		}

		return familySprintList;
	}

	@Override
	@Transactional
	public void assertMembership(Long familyId, String username) {
		boolean result = familyMembershipRepository.existsByFamilyIdAndMemberUsername(familyId, username);
		if (!result) {
			throw new ForbiddenException("사용자는 해당 가족에 속해있지 않습니다.");
		}
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
				Record record = recordRepository.findById(placementDto.getRecordId())
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
	public CreateFamilyResponseDto makeFamily(MakingFamilyRequestDto makingFamilyRequestDto, String username) {
		Member member = memberService.getEntity(username);
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
			.point(0)
			.sprints(null) // setSprints에 의하여 초기화 될 예정
			.items(List.of())
			.build();

		Sprint sprint = Sprint.builder()
			.family(family)
			.records(List.of())
			// .dateRange(getInitDateRange(LocalDate.now()))
			.dateRange(getInitialDateRange())
			.isHarvested(false)
			.build();

		sprintRepository.save(sprint);
		Tree tree = Tree.builder().family(family).placements(List.of()).build();

		family.setSprints(List.of(sprint));
		family.setTree(tree);
		familyRepository.save(family);

		FamilyStatistics familyStatistics = FamilyStatistics.builder()
			.family(family)
			.calendarPlanCount(0)
			.dailyRecordCount(0)
			.challengeCompleteCount(0)
			.eventRecordCount(0)
			.harvestCount(0)
			.build();

		familyStatisticsRepository.save(familyStatistics);
		treeRepository.save(tree);

		FamilyMembership familyMembership = FamilyMembership.builder()
			.family(family)
			.member(member)
			.role(FamilyRole.LEADER)
			.build();

		familyMembershipRepository.save(familyMembership);

		CreateFamilyResponseDto createFamilyResponseDto = CreateFamilyResponseDto.builder()
			.familyId(family.getId())
			.build();
		return createFamilyResponseDto;
	}

	private DateRange getInitialDateRange() {
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = YearMonth.from(startDate).atEndOfMonth();
		return DateRange.builder()
			.startDate(startDate)
			.endDate(endDate)
			.build();
	}

	@Override
	@Transactional
	public void swapSprint(RefreshSprintRequestDto requestDto) {
		Long familyId = requestDto.getFamilyId();
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
		Member member = memberService.getEntity(username);
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

	@Override
	public RafflingResponseDto raffleItem(RafflingRequestDto dto, String username) {
		Long familyId = dto.getFamilyId();
		assertMembership(familyId, username);
		Family family = familyRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("유효하지 않은 가족입니다."));

		List<FamilyItem> collectedItem = familyItemRepository.findAllByFamilyId(familyId);
		Item[] allOfItemList = Item.values();

		int familyPoint = family.getPoint();
		RafflingResponseDto responseDto = new RafflingResponseDto();
		if (familyPoint < RAFFLING_COST) {
			throw new BusinessException("포인트가 부족합니다.");
		}
		if (collectedItem.size() == allOfItemList.length) {
			throw new BusinessException("이미 모든 아이템을 수집했습니다.");
		}
		familyPoint -= RAFFLING_COST;
		family.setPoint(familyPoint);
		responseDto.setFamilyPoint(familyPoint);
		familyRepository.save(family);

		boolean duplication = true;
		while (duplication) {
			int rafflingItemId = (int)(Math.random() * allOfItemList.length);
			Item item = allOfItemList[rafflingItemId];

			if (!familyItemRepository.existsByCode(item)) {
				FamilyItem entity = FamilyItem.builder()
					.family(family)
					.code(item)
					.type(item.getType())
					.build();
				familyItemRepository.save(entity);
				responseDto.setRafflingCode(String.valueOf(item));
				duplication = false;
			}
		}

		return responseDto;
	}

	@Override
	public List<FamilyMemberResponseDto> loadFamilyMemberList(Long familyId, String username) {
		Member me = memberService.getEntity(username);
		List<FamilyMemberResponseDto> familyMembers = memberRepository.findAllByFamilyId(familyId);
		return checkIsMe(familyMembers, me.getId());
	}

	private List<FamilyMemberResponseDto> checkIsMe(List<FamilyMemberResponseDto> familyMembers, Long me) {
		List<FamilyMemberResponseDto> result = new ArrayList<>();

		for (FamilyMemberResponseDto dto : familyMembers) {
			dto.setMe(Objects.equals(dto.getMemberId(), me));
			result.add(dto);
		}
		return result;
	}

	@Override
	public void changeLeader(Long familyId, ChangeLeaderRequestDto requestDto, String pastLeaderName) {
		Long newLeaderId = requestDto.getNewLeaderMemberId();
		Member pastLeader = memberService.getEntity(pastLeaderName);
		Long pastLeaderId = pastLeader.getId();
		FamilyMembership pastLeaderMemberShip = getPastLeaderMembership(familyId, pastLeaderId);
		FamilyMembership newLeaderMemberShip = familyMembershipRepository.findByFamilyIdAndMemberId(familyId,
			newLeaderId).get();
		newLeaderMemberShip.setRole(FamilyRole.LEADER);
		pastLeaderMemberShip.setRole(FamilyRole.MEMBER);

		familyMembershipRepository.save(newLeaderMemberShip);
		familyMembershipRepository.save(pastLeaderMemberShip);
	}

	@Override
	public List<FamilyStatisticsResponseDto> familyAchievementProgress(Long familyId) {
		FamilyStatistics familyStatistics = familyStatisticsRepository.findById(familyId)
			.orElseThrow(() -> new NoSuchContentException("유효하지 않은 가족입니다."));
		FamilyAchievementProgressDto progressDto = FamilyAchievementProgressDto.from(familyStatistics);
		List<Achievement> receivedRewardChallenge = from(
			achievementRewardHistoryRepository.findAllByFamilyIdOrderByIdDesc(familyId));
		progressDto.setReceivedRewardChallenge(receivedRewardChallenge);

		List<FamilyStatisticsResponseDto> responseDtoList = new ArrayList<>();
		for (Achievement achievement : Achievement.values()) {
			int progress = achievement.getGetter().apply(familyStatistics);
			int rewardPoint = achievement.getReward();
			float goal = achievement.getGoal();
			String content = achievement.getContent();

			int percent = 0;
			if (progress >= goal) {
				progress = (int)goal;
				percent = 100;
			} else {
				percent = Math.round((progress / goal) * 100);
			}

			FamilyStatisticsResponseDto responseDto = FamilyStatisticsResponseDto.builder()
				.content(content)
				.rewardPoint(rewardPoint)
				.percent(percent)
				.progress(progress)
				.rewarded(false)
				.build();
			if (progressDto.getReceivedRewardChallenge().contains(achievement))
				responseDto.setRewarded(true);
			responseDtoList.add(responseDto);
		}
		return responseDtoList;
	}

	private List<Achievement> from(List<AchievementRewardHistory> achievementRewardHistoryList) {
		List<Achievement> getAchievementList = new ArrayList<>();
		for (AchievementRewardHistory entity : achievementRewardHistoryList) {
			getAchievementList.add(entity.getAchievement());
		}
		return getAchievementList;
	}

	private FamilyMembership getPastLeaderMembership(Long familyId, Long pastLeaderId) throws BusinessException {
		FamilyMembership pastLeaderMemberShip = familyMembershipRepository.findByFamilyIdAndMemberId(familyId,
			pastLeaderId).get();
		if (!pastLeaderMemberShip.getRole().equals(FamilyRole.LEADER)) {
			throw new ForbiddenException("가장이 아닙니다.");
		}
		return pastLeaderMemberShip;
	}

	@Override
	public FamilyListDto getFamilyList(String username) {
		Optional<Member> memberOptional = Optional.ofNullable(memberService.getEntity(username));
		Member member = memberOptional.orElseThrow(() -> new NoSuchContentException("존재하지 않는 사용자입니다."));

		List<FamilyMembership> familyMemberships = member.getFamilyMemberships();
		List<FamilyListDto.FamilyInfo> familyInfoList = new ArrayList<>();
		familyMemberships.forEach(fm -> {
			familyInfoList.add(new FamilyListDto.FamilyInfo(fm.getFamily().getId(), fm.getFamily().getName()));
		});
		return new FamilyListDto(familyInfoList);
	}
}
