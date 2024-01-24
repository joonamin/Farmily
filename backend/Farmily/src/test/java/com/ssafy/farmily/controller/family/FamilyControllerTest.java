package com.ssafy.farmily.controller.family;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class FamilyControllerTest {
	@Autowired
	MockMvc mockMvc;

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

}
