package com.ssafy.farmily.service.member;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.ImageDto;
import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.dto.oauth.OAuth2Attributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

		// on-premise db에 회원 정보를 저장한다.
		// to-refactor: 이 부분을 successHandler로 빼는 것이 좋을 것 같다.
		if (target.isEmpty())
			join(oAuth2Attributes);

		oidcUser.getAttributes().forEach((k, v) -> log.info("[OidcUser claims] key: {}, value: {}", k, v));
		OidcUserInfo userInfo = new OidcUserInfo(Map.of("sub", oAuth2Attributes.getUsername()));

		return new DefaultOidcUser(oidcUser.getAuthorities(), userRequest.getIdToken(), userInfo);
	}

	private void join(OAuth2Attributes attributes) {
		ImageDto profileImage = ImageDto.builder()
			.location(attributes.getProfilePic())
			.originalFileName(attributes.getUsername() + UUID.randomUUID())
			.build();

		MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
			.username(attributes.getUsername())
			.nickname(attributes.getNickname())
			.profilePic(profileImage)
			.build();
		memberService.join(memberRegisterDto);
	}
}
