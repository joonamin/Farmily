package com.ssafy.farmily.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.farmily.dto.ChangeLeaderRequestDto;
import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMainTreeDto;
import com.ssafy.farmily.dto.FamilyMemberResponseDto;
import com.ssafy.farmily.dto.JoinRequestDto;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.dto.PlacementDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.dto.RefreshSprintRequestDto;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.FamilyItemRepository;
import com.ssafy.farmily.repository.FamilyMembershipRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.repository.TreeRepository;
import com.ssafy.farmily.service.family.FamilyService;
import com.ssafy.farmily.service.file.FileService;
import com.ssafy.farmily.service.record.RecordService;
import com.ssafy.farmily.type.AccessoryType;
import com.ssafy.farmily.type.FamilyRole;
import com.ssafy.farmily.type.Item;
import com.ssafy.farmily.type.ItemType;
import com.ssafy.farmily.type.RecordType;
import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.utils.Position;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("local")
class FamilyServiceTest {
	@Autowired
	FamilyService familyService;
	@Autowired
	RecordRepository recordRepository;
	@Autowired
	FamilyRepository familyRepository;
	@Autowired
	FamilyItemRepository familyItemRepository;
	@Autowired
	RecordService recordService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	SprintRepository sprintRepository;
	@Autowired
	TreeRepository treeRepository;
	@Autowired
	FileService fileService;
	@Autowired
	FamilyMembershipRepository familyMembershipRepository;

	@BeforeEach
	public void beforeEach() {
		Member member1 = Member.builder()
			.username("user")
			.nickname("A")
			.familyMemberships(List.of())
			.build();
		Member member2 = Member.builder()
			.username("member2")
			.nickname("B")
			.familyMemberships(List.of())
			.build();
		memberRepository.save(member1);
		memberRepository.save(member2);

	}

	@Test
	@Transactional
	void 가족생성() throws Exception {
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토",null);
		familyService.makeFamily(makingFamilyRequestDto,member.getUsername());
		// id, name, motto, tree, challengeIds, sprintId
		FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(1L);
		FamilyMainTreeDto tree = FamilyMainTreeDto.from(treeRepository.findById(1L).get());
		Assertions.assertEquals(familyMainDto.getName(), "대한민국");
		Assertions.assertEquals(familyMainDto.getMotto(), "삶의 모토");
		Assertions.assertEquals(familyMainDto.getTree().getId(), 1L);
		Assertions.assertEquals(familyMainDto.getTree(), tree);

	}

	@Test
	@Transactional
	void swapSprint_스프린트제거후생성() {
		// given
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토",null);
		familyService.makeFamily(makingFamilyRequestDto, member.getUsername());
		RefreshSprintRequestDto requestDto = new RefreshSprintRequestDto();
		requestDto.setFamilyId(1L);
		// when
		familyService.swapSprint(requestDto);
		// then
		String now = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
		Assertions.assertEquals(sprintRepository.findByFamilyId(1L).getDateRange().getStartDate(),
			LocalDate.parse(now));
	}

	@Test
	@Transactional
	void setMainFamilyInfo_가족메인정보가져오기() {
		// given
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토",null);
		familyService.makeFamily(makingFamilyRequestDto, member.getUsername());
		RefreshSprintRequestDto requestDto = new RefreshSprintRequestDto();
		requestDto.setFamilyId(1L);
		familyService.swapSprint(requestDto);
		// when
		FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(1L);
		FamilyMainTreeDto tree = FamilyMainTreeDto.from(treeRepository.findById(1L).get());
		// then
		Assertions.assertEquals(familyMainDto.getTree(), tree);
	}

	@Test
	@Transactional
	void getFamilyInventory_가족인벤토리가져오기() {
		// given
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토",null);
		familyService.makeFamily(makingFamilyRequestDto, member.getUsername());
		RefreshSprintRequestDto requestDto = new RefreshSprintRequestDto();
		requestDto.setFamilyId(1L);
		familyService.swapSprint(requestDto);
		Family family = familyRepository.findById(1L).get();
		FamilyItem familyItem1 = FamilyItem.builder()
			.family(family).code(Item.TREE_1).type(ItemType.TREE_SKIN).build();
		FamilyItem familyItem2 = FamilyItem.builder()
			.family(family).code(Item.TREE_2).type(ItemType.ACCESSORY).build();
		familyItemRepository.save(familyItem1); familyItemRepository.save(familyItem2);

		// when
		List<FamilyItemDto> inventory = familyService.getFamilyInventory(1L);

		// then
		Assertions.assertEquals(inventory.get(0).getItemCode(),Item.TREE_1);
		Assertions.assertEquals(inventory.get(1).getType(),ItemType.ACCESSORY);
	}

	@Test
	@Transactional
	void getFamilySprintList_바구니리스트받기(){
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토",null);
		familyService.makeFamily(makingFamilyRequestDto, member.getUsername());
		Family family = familyRepository.findById(1L).get();
		DateRange dateRange = new DateRange();
		dateRange.setStartDate(LocalDate.parse("2023-12-01"));
		dateRange.setEndDate(LocalDate.parse("2023-12-31"));
		Sprint sprint = Sprint.builder().family(family).isHarvested(false).dateRange(dateRange).records(new ArrayList<>()).build();
		sprintRepository.save(sprint);
		RefreshSprintRequestDto requestDto = new RefreshSprintRequestDto();
		requestDto.setFamilyId(1L);
		familyService.swapSprint(requestDto);

		List<FamilyBasketDto> familyBasketDtoList = familyService.getFamilySprintList(1L);

		Assertions.assertEquals(familyBasketDtoList.get(0).getRange().getStartDate(), LocalDate.parse("2023-12-01"));
		Assertions.assertEquals(familyBasketDtoList.get(0).getRange().getEndDate(), LocalDate.parse("2023-12-31"));
	}

	@Test
	@Transactional
	void placingItems_아이템배열대로배치하기(){
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토",null);
		familyService.makeFamily(makingFamilyRequestDto, member.getUsername());
		Family family = familyRepository.findById(1L).get();
		RefreshSprintRequestDto refreshSprintRequestDto = new RefreshSprintRequestDto();
		refreshSprintRequestDto.setFamilyId(1L);
		familyService.swapSprint(refreshSprintRequestDto);
		Sprint sprint = sprintRepository.findById(1L).get();
		Record record = Record
			.builder().id(1L).content("내용").title("제목").author(member).sprint(sprint).type(RecordType.DAILY).build();
		FamilyItem familyItem = FamilyItem.builder().family(family).code(Item.TREE_2).type(ItemType.ACCESSORY).build();
		PlacementDto placementDto1 = new PlacementDto();
		Position position1 = new Position();
		position1.setRow(1); position1.setCol(1);
		placementDto1.setPosition(position1); placementDto1.setDtype("F"); placementDto1.setRecordId(1L);
		PlacementDto placementDto2 = new PlacementDto();
		Position position2 = new Position();
		position2.setCol(2); position2.setRow(2);
		placementDto2.setPosition(position2); placementDto2.setDtype("A"); placementDto2.setType(AccessoryType.HIDDEN_FRUIT);
		List<PlacementDto> list = new ArrayList<>(); list.add(placementDto2); list.add(placementDto1);
		System.out.println(list.size());
		recordRepository.save(record);
		PlacingItemRequestDto requestDto = new PlacingItemRequestDto();
		requestDto.setPlacementDtoList(list); requestDto.setTreeId(1L);
		// when
		familyService.placingItems(requestDto);
		// then
		FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(1L);
		Assertions.assertEquals(familyMainDto.getTree().getMainRecordFruitDtoList().get(0).getRow(),1);
		Assertions.assertEquals(familyMainDto.getTree().getMainAccessoryFruitDtoList().get(0).getRow(),2);
	}

	@Test
	@Transactional
	void loadFamilyMemberList_가족멤버리스트불러오기(){
		Member member1 = memberRepository.findById(1L).get();
		Member member2 = memberRepository.findById(2L).get();
		MakingFamilyRequestDto requestDto = new MakingFamilyRequestDto("대한민국","16강 가즈아",null);
		familyService.makeFamily(requestDto, member1.getUsername());
		String invitationCode = familyRepository.findById(1L).get().getInvitationCode();
		JoinRequestDto joinRequestDto = new JoinRequestDto();
		joinRequestDto.setInvitationCode(invitationCode);
		familyService.insertFamilyMemberShip(joinRequestDto,member2.getUsername());
		List<FamilyMemberResponseDto> list= familyService.loadFamilyMemberList(1L,member1.getUsername());
		Assertions.assertEquals(list.get(0).getRole(),FamilyRole.LEADER);
		Assertions.assertEquals(list.get(0).getNickname(),"A");
		Assertions.assertEquals(list.get(1).getNickname(),"B");
		Assertions.assertEquals(list.get(1).getRole(),FamilyRole.MEMBER);
		Assertions.assertTrue(list.get(0).isMe());
		Assertions.assertFalse(list.get(1).isMe());
	}

	@Test
	@Transactional
	void mendateHeadTest_가장위임(){
		Member member1 = memberRepository.findById(1L).get();
		Member member2 = memberRepository.findById(2L).get();
		MakingFamilyRequestDto requestDto = new MakingFamilyRequestDto("대한민국","16강 가즈아",null);
		familyService.makeFamily(requestDto , member1.getUsername());
		String invitationCode = familyRepository.findById(1L).get().getInvitationCode();
		JoinRequestDto joinRequestDto = new JoinRequestDto();
		joinRequestDto.setInvitationCode(invitationCode);
		familyService.insertFamilyMemberShip(joinRequestDto,member2.getUsername());
		ChangeLeaderRequestDto changeLeaderRequestDto = new ChangeLeaderRequestDto();
		changeLeaderRequestDto.setNewLeaderMemberId(2L);
		familyService.changeLeader(1L,changeLeaderRequestDto,"user");

		List<FamilyMemberResponseDto> list = familyService.loadFamilyMemberList(1L,member1.getUsername());

		Assertions.assertEquals(list.get(0).getRole(),FamilyRole.MEMBER);
		Assertions.assertEquals(list.get(1).getRole(),FamilyRole.LEADER);
	}

	@Nested
	class getFamilyList는 {
		@Nested
		class valid_input_에_대해 {
			String username = "valid_user";

			@Test
			void it_returns_family_list() {
				// 실제 해당 사용자를 insert 하고, 해당 사용자의 가족을 가져오는 것을 테스트
			}

			@Nested
			class invalid_input_에_대해 {
				String username = "NOT_FOUND_USER";

				@Test
				void it_returns_family_list() {
					// NoSuchElements exception을 던진다.
					// org.assertj.core.api.Assertions.assertThatThrow(familyService.getFamilyList(username)).;
					org.assertj.core.api.Assertions.assertThatThrownBy(() -> familyService.getFamilyList(username))
						.isInstanceOf(NoSuchElementException.class);
				}
			}
		}

	}
}
