package com.ssafy.farmily.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;
import com.ssafy.farmily.dto.InsertCommunityPostRequestDto;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.repository.CommunityPostRepository;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.service.community.CommunityService;

// controller, service, repository 해당하는 모든 빈들을 spring container 포함
// @SpringBootTest
// DB에 해당하는 모든 bean 들이 포함되거든?
@SpringBootTest
@AutoConfigureMockMvc
public class CommunityControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	CommunityService communityService;
	@Autowired
	MemberRepository memberRepository;
	@BeforeEach
	public void beforeEach() {
		Member member = Member.builder()
			.nickname("salah")
			.username("KAKAO_156156")
			.password("12345678")
			.familyMemberships(new LinkedList<>())
			.build();
		memberRepository.save(member);
		for(int i = 1; i <= 6; i++){
			InsertCommunityPostRequestDto dto = new InsertCommunityPostRequestDto();
			dto.setTitle("제목입니다" + i); dto.setContent("내용입니다" + i);
			dto.setAuthor(member.getUsername()); dto.setTreeSnapshot(null);

			communityService.insertCommunityPost(dto,member.getUsername());
		}
	}

	/**
	 * given(주어진 상황) when(~동작할 때) then (~를 반환해야한다, 예외를 던져야된다)
	 */
	@Test
	void community처음입장() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/community")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("contents[0].id").value(6L))
			.andExpect(jsonPath("hasNext").value(true));
	}

	@Test
	void community다음list출력() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/community?reqPageNum=1&reqLastSeenId=4")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("contents[0].id").value(3L))
			.andExpect(jsonPath("hasNext").value(false));
	}

	@Test
	void communityPostDetail() throws Exception{
		Long postId = 1L;
		mockMvc.perform(MockMvcRequestBuilders.get("/community/" + postId)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("title").value("제목입니다1"))
			.andExpect(jsonPath("content").value("내용입니다1"));
	}
}
