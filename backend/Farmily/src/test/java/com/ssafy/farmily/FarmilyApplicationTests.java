package com.ssafy.farmily;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.farmily.controller.family.FamilyController;
import com.ssafy.farmily.dto.FamilyMainDTO;
import com.ssafy.farmily.entity.Family;

@SpringBootTest
class FarmilyApplicationTests {
	@Autowired
	FamilyController familyController;
	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("familyId를 받아서 index에 띄어 줄 것들을 보여줘보자")
	void mainIndex(){
		/*
		given
		*/
		Long familyId = 1L;
		FamilyMainDTO familyMainDTO = familyController.mainIndex(familyId);

		/*
		when, then
		*/
		Assertions.assertThat(familyMainDTO.getId()).isEqualTo(1L);
		Assertions.assertThat(familyMainDTO.getName()).isEqualTo("1");
		Assertions.assertThat(familyMainDTO.getTree()).isEqualTo(null);
		Assertions.assertThat(familyMainDTO.getMotto()).isEqualTo(null);
		Assertions.assertThat(familyMainDTO.getChallengesIds()).contains(5L);
	}
}
