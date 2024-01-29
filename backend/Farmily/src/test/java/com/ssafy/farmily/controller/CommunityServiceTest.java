package com.ssafy.farmily.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Slice;

import com.ssafy.farmily.dto.CommunityPostDto;
import com.ssafy.farmily.repository.CommunityPostRepository;
import com.ssafy.farmily.service.community.CommunityService;

// controller, service, repository 해당하는 모든 빈들을 spring container 포함
// @SpringBootTest
// DB에 해당하는 모든 bean 들이 포함되거든?
@DataJpaTest
public class CommunityServiceTest {
	@Autowired
	CommunityPostRepository communityPostRepository;

	@Autowired
	CommunityService communityService;

	@BeforeEach
	public void beforeEach() {
		// List<CommunityPost> communityPostDtoList = new ArrayList<>();
		// CommunityPost communityPostDto1 = new CommunityPostDto();
		// CommunityPost communityPostDto2 = new CommunityPostDto();
		// CommunityPost communityPostDto3 = new CommunityPostDto();
		//
		// communityPostDto1.setId(1L);communityPostDto1.setId(2L);communityPostDto1.setId(3L);
		// communityPostDtoList.add(communityPostDto1);
		// communityPostDtoList.add(communityPostDto2);
		// communityPostDtoList.add(communityPostDto3);
		//
		// int generatedId = 1L;
		// for (CommunityPost post : communityPostDtoList) {
		// 	post.setId(generatedId++);
		// }
	}

	/**
	 * given(주어진 상황) when(~동작할 때) then (~를 반환해야한다, 예외를 던져야된다)
	 */
	@Test
	void 잘받아오냐() {
		Slice<CommunityPostDto> communityPostList = communityService.getCommunityPostList(4);
		int counter = 0;
		while (communityPostList.hasNext()) {
			counter++;
		}
		Assertions.assertThat(counter).isEqualTo(4);
	}
}
