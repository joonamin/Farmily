package com.ssafy.farmily.service.community;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.ssafy.farmily.dto.CommunityPostDto;

public interface CommunityService {
	Slice<CommunityPostDto> getCommunityPostList(int size);
}
