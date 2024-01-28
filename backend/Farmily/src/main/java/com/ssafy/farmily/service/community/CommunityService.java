package com.ssafy.farmily.service.community;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.ssafy.farmily.dto.CommunityPostDetailDto;
import com.ssafy.farmily.dto.CommunityPostDto;
import com.ssafy.farmily.dto.InsertCommunityPostRequestDto;

public interface CommunityService {
	Slice<CommunityPostDto> getCommunityPostList(int size, int pageNum, Long lastSeenId);
	String insertCommunityPost(InsertCommunityPostRequestDto requestDto);

	CommunityPostDetailDto getPostDetail(Long postId);
}
