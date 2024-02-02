package com.ssafy.farmily.service;

import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.farmily.dto.CommunityPostDetailDto;
import com.ssafy.farmily.dto.CommunityPostDto;
import com.ssafy.farmily.dto.InsertCommunityPostRequestDto;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.repository.CommunityPostRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.MemberRepository;

import com.ssafy.farmily.service.community.CommunityService;
import com.ssafy.farmily.utils.SliceResponse;

@SpringBootTest
@ActiveProfiles("local")
public class CommunityServiceTest {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	FamilyRepository familyRepository;
	@Autowired
	CommunityService communityService;
	@Autowired
	CommunityPostRepository communityPostRepository;
	@BeforeEach
	public void beforeEach() {
		Member member = Member.builder()
			.nickname("salah")
			.username("KAKAO_156156")
			.password("12345678")
			.familyMemberships(new LinkedList<>())
			.build();
		memberRepository.save(member);

	}

	@Test
	void getPostDetail_커뮤글확인(){
		// given
		Member member = memberRepository.findById(1L).get();
		InsertCommunityPostRequestDto dto = new InsertCommunityPostRequestDto();
		dto.setTitle("제목입니다"); dto.setContent("내용입니다"); dto.setAuthor(member.getUsername());
		communityService.insertCommunityPost(dto, member.getUsername());

		// when
		CommunityPostDetailDto getPost = communityService.getPostDetail(1L);

		// then
		Assertions.assertEquals(getPost.getTitle(),"제목입니다");
	}

	@Test
	void getCommunityPostList_커뮤니티게시글리스트_불러오기(){
		Member member = memberRepository.findById(1L).get();
		for(int i = 0; i < 6; i++){
			InsertCommunityPostRequestDto dto = new InsertCommunityPostRequestDto();
			dto.setTitle("제목입니다" + i); dto.setContent("내용입니다" + i); dto.setAuthor(member.getUsername());
			communityService.insertCommunityPost(dto, member.getUsername());
		}

		// when
		SliceResponse<CommunityPostDto> list = communityService.getCommunityPostList(3,0,null);
		// then
		Assertions.assertTrue(list.isHasNext());
		Assertions.assertEquals(list.getPageNum(),0);
		Assertions.assertEquals(list.getContents().get(0).getId(),6L);
	}
}
