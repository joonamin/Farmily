package com.ssafy.farmily.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.FamilyDisplayDto;
import com.ssafy.farmily.dto.MemberEditRequestDto;
import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.service.family.FamilyMembershipService;
import com.ssafy.farmily.service.member.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final FamilyMembershipService familyMembershipService;

	@PutMapping
	@Operation(
		summary = "사용자 정보 수정",
		description = "사용자 정보를 수정합니다."
	)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "수정 성공")
	})
	public ResponseEntity<Void> put(
		@AuthenticationPrincipal String username,
		@RequestBody MemberEditRequestDto dto
	) {
		memberService.editMemberInfo(username, dto);

		return ResponseEntity.ok().build();
	}

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
