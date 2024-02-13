package com.ssafy.farmily.service.community;

import com.ssafy.farmily.dto.CommunityPostDetailDto;
import com.ssafy.farmily.dto.CommunityPostDto;
import com.ssafy.farmily.dto.InsertCommunityPostRequestDto;

import com.ssafy.farmily.utils.SliceResponse;

public interface CommunityService {
	SliceResponse<CommunityPostDto> getCommunityPostList(int size, int pageNum, Long lastSeenId);
	void insertCommunityPost(InsertCommunityPostRequestDto requestDto, String username);

	void putCommunityPost(InsertCommunityPostRequestDto requestDto, String username, Long communityPostId);
	CommunityPostDetailDto getPostDetail(Long postId);
}
