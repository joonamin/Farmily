package com.ssafy.farmily.service.member;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.ImageDto;
import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.dto.oauth.OAuth2Attributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOidcMemberService extends OidcUserService {

	private final MemberService memberService;

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);

		OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(userRequest.getClientRegistration().getRegistrationId(),
			oidcUser.getAttributes());

		Optional<MemberInfoDto> target = memberService.getMember(oAuth2Attributes.getUsername());
		target.ifPresentOrElse(this::login, () -> join(oAuth2Attributes));
		return oidcUser;
	}

	private void login(MemberInfoDto memberInfoDto) {
		// todo: 추후에 jwt 관련 발급을 여기서 처리한다.
		log.info("로그인 성공: {}", memberInfoDto.getUsername());
	}

	private void join(OAuth2Attributes attributes) {
		ImageDto profileImage = ImageDto.builder()
			.location(attributes.getProfilePic())
			.originalFileName(attributes.getUsername() + UUID.randomUUID().toString())
			.build();

		MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
			.username(attributes.getUsername())
			.nickname(attributes.getNickname())
			.profilePic(profileImage)
			.build();
		memberService.join(memberRegisterDto);
	}
}
