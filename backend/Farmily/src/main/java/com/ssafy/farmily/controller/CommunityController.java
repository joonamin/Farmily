package com.ssafy.farmily.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.CommunityPostDetailDto;
import com.ssafy.farmily.dto.CommunityPostDto;
import com.ssafy.farmily.dto.InsertCommunityPostRequestDto;
import com.ssafy.farmily.service.community.CommunityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.ssafy.farmily.utils.SliceResponse;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
	private final CommunityService communityService;

	@GetMapping("")
	@Operation(
		summary = "커뮤니티 목록 불러오기",
		description = "slice 객체로 무한 스크롤 구현"
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "커뮤니티 목록 불러오기 성공"
		)
	})
	public ResponseEntity<SliceResponse<CommunityPostDto>> communityIndex(
		@RequestParam(value = "reqPageNum", required = false, defaultValue = "0") Integer reqPageNum,
		@RequestParam(value = "reqLastSeenId", required = false) Long reqLastSeenId
	) {
		SliceResponse<CommunityPostDto> communityPostDtoList =
			communityService.getCommunityPostList(8, reqPageNum, reqLastSeenId);
		return ResponseEntity.ok(communityPostDtoList);
	}

	@GetMapping("/{communityPostId}")
	@Operation(
		summary = "post Detail",
		description = "커뮤니티 포스트의 detail을 리턴합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "post get success"
		)
	})
	public ResponseEntity<CommunityPostDetailDto> detailPost(@PathVariable("communityPostId") Long communityPostId) {
		CommunityPostDetailDto communityPostDetailDto = communityService.getPostDetail(communityPostId);
		return ResponseEntity.ok(communityPostDetailDto);
	}

	@PostMapping("/insert")
	@Operation(
		summary = "커뮤니티 post insert",
		description = "커뮤니티 post를 생성합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "post insert success"
		)
	})
	public ResponseEntity<String> insertPost(
		@Valid InsertCommunityPostRequestDto insertPostRequestDto,
		@AuthenticationPrincipal String username
	) {
		communityService.insertCommunityPost(insertPostRequestDto, username);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{communityPostId}")
	public ResponseEntity<Void> putCommunityPost(
		@AuthenticationPrincipal String username,
		@PathVariable Long communityPostId,
		@Valid InsertCommunityPostRequestDto putPostRequestDto
	){
		communityService.putCommunityPost(putPostRequestDto,username,communityPostId);
		return ResponseEntity.ok().build();
	}

	/*
	TODO
	 삭제 코드
	 @DeleteMapping
	 */
}
