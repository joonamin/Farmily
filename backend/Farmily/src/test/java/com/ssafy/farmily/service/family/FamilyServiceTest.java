package com.ssafy.farmily.service.family;

import static com.ssafy.farmily.type.FamilyRole.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMainTreeDto;
import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.entity.FamilyMembership;
import com.ssafy.farmily.entity.FruitPlacement;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Placement;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.entity.Tree;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.FamilyItemRepository;
import com.ssafy.farmily.repository.FamilyMembershipRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.repository.PlacementRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.repository.TreeRepository;
import com.ssafy.farmily.type.Item;
import com.ssafy.farmily.type.ItemType;
import com.ssafy.farmily.type.RecordType;
import com.ssafy.farmily.type.TreeType;

import jakarta.transaction.Transactional;
import utils.DateRange;
import utils.Position;

@SpringBootTest
@AutoConfigureMockMvc
class FamilyServiceTest {

	private final FamilyService familyService;
	private final TreeRepository treeRepository;
	private final RecordRepository recordRepository;
	private final SprintRepository sprintRepository;
	private final MemberRepository memberRepository;
	private final FamilyRepository familyRepository;
	private final FamilyMembershipRepository familyMembershipRepository;
	private final FamilyItemRepository familyItemRepository;
	private final PlacementRepository placementRepository;
	@Autowired
	public FamilyServiceTest(FamilyService familyService, TreeRepository treeRepository,
		RecordRepository recordRepository, SprintRepository sprintRepository, MemberRepository memberRepository, FamilyRepository familyRepository,
		FamilyMembershipRepository familyMembershipRepository, FamilyItemRepository familyItemRepository,
		PlacementRepository placementRepository) {
		this.familyService = familyService;
		this.treeRepository = treeRepository;
		this.recordRepository = recordRepository;
		this.sprintRepository = sprintRepository;
		this.memberRepository = memberRepository;
		this.familyRepository = familyRepository;
		this.familyMembershipRepository = familyMembershipRepository;
		this.familyItemRepository = familyItemRepository;
		this.placementRepository = placementRepository;
	}
	@BeforeEach
	public void beforeEach(){
		// INSERT INTO member (created_at,nickname,PASSWORD,username) VALUES (NOW(),"salah","12345678","USER@gamil.com");
		// INSERT INTO family (POINT,created_at,invitation_code,motto,NAME) VALUES (0,NOW(),"invite","안녕하세요","대한민국");
		// INSERT INTO family_membership (ROLE,created_at,family_id,member_id) VALUES("1",NOW(),1,1);
		// INSERT INTO tree (id, created_at) VALUES (1,NOW());
		// INSERT INTO sprint(end_date, is_harvested, start_date, created_at, family_id) VALUES ("2024-01-31",FALSE,CURDATE(), NOW(),1);

		Member member = Member.builder()
			.nickname("salah")
			.username("KAKAO_156156")
			.password("12345678")
			.build();
		memberRepository.save(member);

		Family family = Family.builder()
			.point(100).invitationCode("invite").motto("안녕하세요").name("대한민국").build();

		FamilyMembership familyMembership = FamilyMembership.builder()
			.role(LEADER).family(family).member(member).build();
		familyMembershipRepository.save(familyMembership);


		Tree tree = Tree.builder().family(family).type(TreeType.BASIC).build();
		family.setTree(tree);
		treeRepository.save(tree);
		familyRepository.save(family);
		DateRange dateRange1 = new DateRange();
		dateRange1.setStartDate(LocalDate.parse("2024-01-01"));
		dateRange1.setEndDate(LocalDate.parse("2024-01-31"));


		DateRange dateRange2 = new DateRange();
		dateRange2.setStartDate(LocalDate.parse("2023-12-01"));
		dateRange2.setEndDate(LocalDate.parse("2023-12-31"));

		List<Sprint> list = new ArrayList<>();
		Sprint sprint1 = Sprint.builder()
			.dateRange(dateRange1).isHarvested(false).family(family).build();
		Sprint sprint2 = Sprint.builder()
			.dateRange(dateRange2).isHarvested(true).family(family).build();
		List<Record> pastRecords = new ArrayList<>();
		Record pastRecord = Record.builder().author(member).sprint(sprint1).type(RecordType.DAILY).content("2023년").title("12월").build();
		pastRecords.add(pastRecord);
		sprint2.setRecords(pastRecords);
		sprintRepository.save(sprint1);
		sprintRepository.save(sprint2);

		// INSERT INTO record (author_id, created_at,id,sprint_id,dtype) VALUES (1,NOW(),1,1,"C");
		// INSERT INTO record (author_id,created_at, sprint_id, dtype,content,title) VALUES (1,NOW(),1,"D","찬밥아 팽이 좀 작작 돌아라", "이강인은 월클이 아니다");
		// INSERT INTO challenge_record (end_date,is_rewarded,start_date,id) VALUES ("2024-01-29",FALSE,CURDATE(),1);
		// INSERT INTO family_item (created_at,family_id,CODE,TYPE) VALUES (NOW(),1,"F","ACCESSORY");
		// INSERT INTO placement (col,ROW,created_at,record_id,tree_id,dtype) VALUES (1,1,NOW(),1,1,"D");

		List<Record> recordList = new ArrayList<>();
		DateRange dateRange = new DateRange();
		dateRange.setStartDate(LocalDate.parse("2024-01-21")); dateRange.setEndDate(LocalDate.parse("2024-01-28"));

		Record record1 = Record.builder().author(member).sprint(sprint1).type(RecordType.DAILY).content("찬밥아 팽이 좀 작작 돌아라").title("이강인은 월클이 아니다").build();
		ChallengeRecord record2 = ChallengeRecord.builder().author(member).sprint(sprint1).type(RecordType.CHALLENGE).dateRange(dateRange).isRewarded(Boolean.FALSE).build();
		recordList.add(record1); recordList.add(record2); sprint1.setRecords(recordList);
		recordRepository.saveAll(recordList);

		FamilyItem familyItem = FamilyItem.builder().type(ItemType.TREE_SKIN).family(family).code(Item.TREE_1).build();
		familyItemRepository.save(familyItem);

		Position position = new Position();
		position.setCol(1); position.setRow(1);
		List<Placement> placementList = new ArrayList<>();
		FruitPlacement placement = FruitPlacement.builder().record(record1).position(position).build();
		placementList.add(placement);
		tree.setPlacements(placementList);
		placementRepository.save(placement);
	}
	@Test
	@DisplayName("Main에 노출할 Family 정보를 잘 가져오는지 함 보입시더")
	@Transactional
	void 메인테스트() throws Exception {
		// given
		Long familyId = 1L;
		// when
		FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(familyId);
		FamilyMainTreeDto tree = FamilyMainTreeDto.from(treeRepository.findById(familyId).get());
		List<Long> challenges = recordRepository.findCurrentChallenges(familyId);

		FamilyMainDto findDto = new FamilyMainDto();
		findDto.setId(1L);
		findDto.setName("대한민국");
		findDto.setMotto("안녕하세요");
		findDto.setTree(tree);
		findDto.setChallengesIds(challenges);

		// then
		Assertions.assertEquals(findDto.toString(), familyMainDto.toString());

	}

	@Test
	@DisplayName("메인에서 없는 familyId를 들고 왔을 때")
	@Transactional
	void 메인에러테스트() {
		// given
		Long familyId = 2L;

		Assertions.assertThrows(NoSuchContentException.class, () -> {
			FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(familyId);
		});
	}

	@Test
	@DisplayName("inventory 주세요")
	@Transactional
	void getFamilyInventory() {
		// given
		Long familyId = 1L;
		// when
		List<FamilyItemDto> list = familyService.getFamilyInventory(familyId);

		Assertions.assertEquals(list.get(0).getId(), 1);
		Assertions.assertEquals(list.get(0).getItemCode().toString(), "TREE_1");
		Assertions.assertEquals(list.get(0).getType().toString(), "TREE_SKIN");
	}

	@Test
	@DisplayName("없는 familyId를 들고 inventory를 달라고 할 때")
	@Transactional
	void 없는가족가방() {
		Long familyId = 2L;

		Assertions.assertThrows(NoSuchContentException.class, () -> {
			List<FamilyItemDto> familyInventory = familyService.getFamilyInventory(familyId);
		});
	}


	@Test
	@DisplayName("바구니 들고오기")
	@Transactional
	void getBasketList() {
		// given
		Long familyId = 1L;

		// when
		List<FamilyBasketDto> familySprintList = new ArrayList<>();
		List<Sprint> temp2 = sprintRepository.findAllByFamilyIdAndIsHarvested(familyId, true);
		for (Sprint sprint : temp2) {
			FamilyBasketDto familyBasketDTO = new FamilyBasketDto().of(sprint);
			familySprintList.add(familyBasketDTO);
		}

		Assertions.assertEquals(familySprintList.get(0).getRange().getStartDate().toString(), "2023-12-01");
		Assertions.assertEquals(familySprintList.get(0).getRange().getEndDate().toString(), "2023-12-31");

	}

	@Test
	@DisplayName("없는 familyId를 들고 basket을 달라고 할 때")
	@Transactional
	void 없는가족바구니() {
		Long familyId = 2L;
		Assertions.assertThrows(NoSuchContentException.class, () -> {
			List<FamilyBasketDto> familyBasketDtoList = familyService.getFamilySprintList(familyId);
		});
	}
}
