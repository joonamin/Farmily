package com.ssafy.farmily.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.farmily.dto.MakingFamilyRequestDto;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.repository.MemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class FamilyControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	ObjectMapper objectMapper;
	@BeforeEach
	public void beforeEach(){
		Member member1 = Member.builder()
			.username("user")
			.nickname("A")
			.build();
		Member member2 = Member.builder()
			.username("member2")
			.nickname("B")
			.build();
		memberRepository.save(member1);
		memberRepository.save(member2);
	}

	@Test
	@DisplayName("test1")
	void 메인테스트() throws Exception {
		// given
		Long familyId = 1L;

		mockMvc.perform(MockMvcRequestBuilders.get("/family/" + familyId)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("name").value("대한민국"));
	}

	@Test
	@DisplayName("familyId를 받아서 inventory에 있는 item을 출력해보자")
	void inventoryTest() throws Exception {
		Long familyId = 1L;
		mockMvc.perform(MockMvcRequestBuilders.get("/family/" + familyId + "/inventory")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("[0].id").value(1));
	}

	@Test
	@DisplayName("familyId를 받아서 이때까지의 바구니를 출력해보자")
	void basketTest() throws Exception {
		Long familyId = 1L;
		mockMvc.perform(MockMvcRequestBuilders.get("/family/" + familyId + "/basket")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.[0].range.startDate[0]").value(2023))
			.andExpect(jsonPath("$.[0].range.startDate[1]").value(12))
			.andExpect(jsonPath("$.[0].range.startDate[2]").value(1))
			.andExpect(jsonPath("$.[0].id").value(2));
	}

	@Test
	@WithMockUser
	@Transactional
	void makeFamilyTest() throws Exception {
		Member member = memberRepository.findById(1L).get();
		String content = objectMapper.writeValueAsString(new MakingFamilyRequestDto("대한민국","16강진출기원", null));
		mockMvc.perform(post("/insertFamily")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

}
