package com.ssafy.farmily.service.family;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.FamilyMainTreeDto;
import com.ssafy.farmily.dtoFactory.GetFamily;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.TreeRepository;

import org.junit.jupiter.api.*;

@SpringBootTest
public class FamilyServiceTest {
	@Autowired
	FamilyService familyService;
	@Autowired
	TreeRepository treeRepository;
	@Autowired
	RecordRepository recordRepository;

	@Test
	@DisplayName("test1")
	void 메인테스트() throws Exception {
		// given
		Long familyId = 1L;

		// when
		FamilyMainDto familyMainDto = familyService.setMainFamilyInfo(familyId);
		FamilyMainTreeDto tree = GetFamily.treeToDTO(treeRepository.findById(familyId).get());
		List<Long> challenges = recordRepository.findCurrentChallenges(familyId);

		FamilyMainDto findDto = new FamilyMainDto();
		findDto.setId(1L); findDto.setName("대한민국"); findDto.setMotto("안녕하세요"); findDto.setTree(tree); findDto.setChallengesIds(challenges);
		// then


	}

	@Test
	@DisplayName("familyId를 받아서 inventory에 있는 item을 출력해보자")
	void inventoryTest() throws Exception {
		Long familyId = 1L;
		mockMvc.perform(MockMvcRequestBuilders.get("/family/" + familyId + "/inventory")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.data[0].id").value(1))
			.andExpect(jsonPath("$.data[0].itemCode").value("TREE_1"))
			.andExpect(jsonPath("$.data[0].type").value("TREE_SKIN"));
	}

	@Test
	@DisplayName("familyId를 받아서 이때까지의 바구니를 출력해보자")
	void basketTest() throws Exception {
		Long familyId = 1L;
		mockMvc.perform(MockMvcRequestBuilders.get("/family/" + familyId + "/basket")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.data[0].range.startDate[0]").value(2023))
			.andExpect(jsonPath("$.data[0].range.startDate[1]").value(12))
			.andExpect(jsonPath("$.data[0].range.startDate[2]").value(1))
			.andExpect(jsonPath("$.data[0].id").value(2));
	}
}
