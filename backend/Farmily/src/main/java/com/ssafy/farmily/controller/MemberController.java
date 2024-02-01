package com.ssafy.farmily.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.FamilyDisplayDto;
import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.service.family.FamilyMembershipService;
import com.ssafy.farmily.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;
	private final FamilyMembershipService familyMembershipService;

	@GetMapping("/me")
	public ResponseEntity<MemberInfoDto> getMyInfo(@AuthenticationPrincipal String username) {
		MemberInfoDto memberInfoDto = memberService.getMember(username)
			.orElseThrow(() -> new BusinessException("해당 유저의 정보를 load하는 것에 실패하였습니다"));
		return ResponseEntity.ok(memberInfoDto);
	}

	@GetMapping("/family")
	public ResponseEntity<List<FamilyDisplayDto>> getMyFamily(@AuthenticationPrincipal String username) {
		List<FamilyDisplayDto> familyDisplayDto = familyMembershipService.getFamilies(username);
		return ResponseEntity.ok(familyDisplayDto);
	}

}
