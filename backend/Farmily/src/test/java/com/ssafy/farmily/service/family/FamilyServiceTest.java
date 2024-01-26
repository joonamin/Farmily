package com.ssafy.farmily.service.family;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMainTreeDto;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.repository.TreeRepository;
import com.ssafy.farmily.service.file.FileService;
import com.ssafy.farmily.service.record.RecordService;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("local")
class FamilyServiceTest {
	@Autowired
	FamilyService familyService;

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

	@BeforeEach
	public void beforeEach() {
		// INSERT INTO member (created_at,nickname,PASSWORD,username) VALUES (NOW(),"salah","12345678","USER@gamil.com");
		// INSERT INTO family (POINT,created_at,invitation_code,motto,NAME) VALUES (0,NOW(),"invite","안녕하세요","대한민국");
		// INSERT INTO family_membership (ROLE,created_at,family_id,member_id) VALUES("1",NOW(),1,1);
		// INSERT INTO tree (id, created_at) VALUES (1,NOW());
		// INSERT INTO sprint(end_date, is_harvested, start_date, created_at, family_id) VALUES ("2024-01-31",FALSE,CURDATE(), NOW(),1);

		Member member = Member.builder()
			.nickname("salah")
			.username("KAKAO_156156")
			.password("12345678")
			.familyMemberships(new LinkedList<>())
			.build();
		memberRepository.save(member);
	}

	@Test
	@Transactional
	void 가족생성() throws Exception {
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토", member);
		familyService.makeFamily(makingFamilyRequestDto);
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
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토", member);
		familyService.makeFamily(makingFamilyRequestDto);
		// when
		familyService.swapSprint(1L);
		// then
		Assertions.assertEquals(sprintRepository.findByFamilyId(1L).getDateRange().getStartDate(),
			LocalDate.parse("2024-01-26"));
	}

	@Test
	@Transactional
	void setMainFamilyInfo_가족메인정보가져오기() {
		// given
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토", member);
		familyService.makeFamily(makingFamilyRequestDto);
		familyService.swapSprint(1L);
		// when
		FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(1L);
		FamilyMainTreeDto tree = FamilyMainTreeDto.from(treeRepository.findById(1L).get());
		// then
		Assertions.assertEquals(familyMainDto.getTree(), tree);
	}

	@Test
	@Transactional
	void getFamilyInventory_가족인벤토리가져오기() {
		Member member = memberRepository.findById(1L).get();
		MakingFamilyRequestDto makingFamilyRequestDto = new MakingFamilyRequestDto("대한민국", "삶의 모토", member);
		familyService.makeFamily(makingFamilyRequestDto);
		familyService.swapSprint(1L);

	}
	// @Test
	// @DisplayName("메인에서 없는 familyId를 들고 왔을 때")
	// @Transactional
	// void 메인에러테스트() {
	// 	// given
	// 	Long familyId = 2L;
	//
	// 	Assertions.assertThrows(NoSuchContentException.class, () -> {
	// 		FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(familyId);
	// 	});
	// }
	//
	// @Test
	// @DisplayName("inventory 주세요")
	// @Transactional
	// void getFamilyInventory() {
	// 	// given
	// 	Long familyId = 1L;
	// 	// when
	// 	List<FamilyItemDto> list = familyService.getFamilyInventory(familyId);
	//
	// 	Assertions.assertEquals(list.get(0).getId(), 1);
	// 	Assertions.assertEquals(list.get(0).getItemCode().toString(), "TREE_1");
	// 	Assertions.assertEquals(list.get(0).getType().toString(), "TREE_SKIN");
	// }
	//
	// @Test
	// @DisplayName("없는 familyId를 들고 inventory를 달라고 할 때")
	// @Transactional
	// void 없는가족가방() {
	// 	Long familyId = 2L;
	//
	// 	Assertions.assertThrows(NoSuchContentException.class, () -> {
	// 		List<FamilyItemDto> familyInventory = familyService.getFamilyInventory(familyId);
	// 	});
	// }

	// @Test
	// @DisplayName("바구니 들고오기")
	// @Transactional
	// void getBasketList() {
	// 	// given
	// 	Long familyId = 1L;
	//
	// 	// when
	// 	List<FamilyBasketDto> familySprintList = new ArrayList<>();
	// 	List<Sprint> temp2 = sprintRepository.findAllByFamilyIdAndIsHarvested(familyId, true);
	// 	for (Sprint sprint : temp2) {
	// 		FamilyBasketDto familyBasketDTO = new FamilyBasketDto().of(sprint);
	// 		familySprintList.add(familyBasketDTO);
	// 	}
	//
	// 	Assertions.assertEquals(familySprintList.get(0).getRange().getStartDate().toString(), "2023-12-01");
	// 	Assertions.assertEquals(familySprintList.get(0).getRange().getEndDate().toString(), "2023-12-31");
	//
	// }
	//
	// @Test
	// @DisplayName("없는 familyId를 들고 basket을 달라고 할 때")
	// @Transactional
	// void 없는가족바구니() {
	// 	Long familyId = 2L;
	// 	Assertions.assertThrows(NoSuchContentException.class, () -> {
	// 		List<FamilyBasketDto> familyBasketDtoList = familyService.getFamilySprintList(familyId);
	// 	});
	// }
}
