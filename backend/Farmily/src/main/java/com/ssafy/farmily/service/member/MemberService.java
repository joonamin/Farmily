package com.ssafy.farmily.service.member;

import java.util.Optional;

import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.entity.Member;

public interface MemberService {
	MemberInfoDto join(MemberRegisterDto memberRegisterDto);
	Optional<MemberInfoDto> getMember(String username);
	Member getEntity(String username);

	void assertAuthorship(Member authorEntity, String username);
}
