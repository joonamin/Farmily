package com.ssafy.farmily.service.member;

import java.util.Optional;

import com.ssafy.farmily.dto.LogoutRequestDto;
import com.ssafy.farmily.dto.MemberEditRequestDto;
import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.entity.Member;

public interface MemberService {
	MemberInfoDto join(MemberRegisterDto memberRegisterDto);
	Optional<MemberInfoDto> getMember(String username);
	Member getEntity(String username);

	LogoutResponseDto logout(LogoutRequestDto logoutRequest);
	void assertAuthorship(Member authorEntity, String username);

	void editMemberInfo(String username, MemberEditRequestDto dto);
}
