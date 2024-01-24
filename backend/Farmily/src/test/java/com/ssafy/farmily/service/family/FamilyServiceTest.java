package com.ssafy.farmily.service.family;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMainTreeDto;
import com.ssafy.farmily.dtoFactory.GetFamily;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.repository.TreeRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class FamilyServiceTest {

	private final FamilyService familyService;
	private final TreeRepository treeRepository;
	private final RecordRepository recordRepository;
	private final SprintRepository sprintRepository;

	@Autowired
	public FamilyServiceTest(FamilyService familyService, TreeRepository treeRepository,
		RecordRepository recordRepository, SprintRepository sprintRepository) {
		this.familyService = familyService;
		this.treeRepository = treeRepository;
		this.recordRepository = recordRepository;
		this.sprintRepository = sprintRepository;
	}

	@Test
	@DisplayName("Main에 노출할 Family 정보를 잘 가져오는지 함 보입시더")
	@Transactional
	void 메인테스트() throws Exception {
		// given
		Long familyId = 1L;

		// when
		FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(familyId);
		FamilyMainTreeDto tree = GetFamily.treeToDTO(treeRepository.findById(familyId).get());
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
	void 메인에러테스트() {
		// given
		Long familyId = 2L;

		Assertions.assertThrows(NoSuchContentException.class, () -> {
			FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(familyId);
		});
	}

	@Test
	@DisplayName("Family 가방을 잘 들고 오는지 함 보입시더")
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
	void 없는가족가방() {
		Long familyId = 2L;

		Assertions.assertThrows(NoSuchContentException.class, () -> {
			List<FamilyItemDto> familyInventory = familyService.getFamilyInventory(familyId);
		});
	}


	@Test
	@DisplayName("바구니 들고오기")
	void getBasketList() {
		// given
		Long familyId = 1L;

		// when
		List<FamilyBasketDto> familySprintList = new ArrayList<>();
		List<Sprint> temp2 = sprintRepository.findByFamilyIdAndIsHarvested(familyId, true);
		for (Sprint sprint : temp2) {
			FamilyBasketDto familyBasketDTO = new FamilyBasketDto().of(sprint);
			familySprintList.add(familyBasketDTO);
		}

		Assertions.assertEquals(familySprintList.get(0).getRange().getStartDate().toString(), "2023-12-01");
		Assertions.assertEquals(familySprintList.get(0).getRange().getEndDate().toString(), "2023-12-31");

	}

	@Test
	@DisplayName("없는 familyId를 들고 inventory를 달라고 할 때")
	void 없는가족바구니() {
		Long familyId = 2L;
		Assertions.assertThrows(NoSuchContentException.class, () -> {
			List<FamilyBasketDto> familyBasketDtoList = familyService.getFamilySprintList(familyId);
		});
	}
}
