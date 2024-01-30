package com.ssafy.farmily.service.member;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.dto.oauth.LoginResponseDto;

public interface MemberService {
	MemberInfoDto join(MemberRegisterDto memberRegisterDto);
	Optional<MemberInfoDto> getMember(String username);
}
