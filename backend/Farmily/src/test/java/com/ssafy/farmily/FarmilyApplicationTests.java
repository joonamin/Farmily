package com.ssafy.farmily;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.ssafy.farmily.common.Message;
import com.ssafy.farmily.controller.family.FamilyController;

@SpringBootTest
class FarmilyApplicationTests {
	@Autowired
	FamilyController familyController;
	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
	}
}
