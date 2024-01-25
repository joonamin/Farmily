package com.ssafy.farmily.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.CommunityPostDto;
import com.ssafy.farmily.dto.PageResponseDto;
import com.ssafy.farmily.entity.CommunityPost;

@RestController
@RequestMapping("/community")
public class CommunityController {
	@GetMapping("")
	public PageResponseDto communityIndex(){

	}

	@GetMapping("/{communityPostId}")
	public CommunityPostDto detailPost(@PathVariable Long communityPostId){

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
