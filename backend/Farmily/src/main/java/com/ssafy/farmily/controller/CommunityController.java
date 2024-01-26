package com.ssafy.farmily.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.CommunityPostDto;
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

	// @GetMapping("/{communityPostId}")
	// public CommunityPostDto detailPost(@PathVariable Long communityPostId) {
	//
	// }

	/*
	수정 코드
	@PutMapping
	 */

	/*
	삭제 코드
	@DeleteMapping
	 */
}
