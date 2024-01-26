package com.ssafy.farmily.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.CommunityPostDetailDto;
import com.ssafy.farmily.dto.CommunityPostDto;
import com.ssafy.farmily.dto.InsertCommunityPostRequestDto;
import com.ssafy.farmily.dto.PageResponseDto;
import com.ssafy.farmily.service.community.CommunityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
	private final CommunityService communityService;
	@GetMapping("")
	public ResponseEntity<Slice<CommunityPostDto>> communityIndex() {
		Slice<CommunityPostDto> communityPostDtoList = communityService.getCommunityPostList(6);
		return ResponseEntity.ok(communityPostDtoList);
	}

	@GetMapping("/{communityPostId}")
	public ResponseEntity<CommunityPostDetailDto> detailPost(@PathVariable Long communityPostId) {
		CommunityPostDetailDto communityPostDetailDto = communityService.getPostDetail(communityPostId);
		return ResponseEntity.ok(communityPostDetailDto);
	}

	@PostMapping("/insert")
	public ResponseEntity<String> isnertPost(InsertCommunityPostRequestDto insertPostRequestDto){
		String result = communityService.insertCommunityPost(insertPostRequestDto);
		return ResponseEntity.ok(result);
	}
	/*
	수정 코드
	@PutMapping
	 */

	/*
	삭제 코드
	@DeleteMapping
	 */
}
