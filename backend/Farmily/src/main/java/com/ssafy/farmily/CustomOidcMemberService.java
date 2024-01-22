package com.ssafy.farmily;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.farmily.dto.ImageDto;
import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.exception.InvalidEmailRegistrationException;
import com.ssafy.farmily.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import utils.UserNameGenerator;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOidcMemberService extends OidcUserService {

	private final MemberService memberService;

	@SneakyThrows
	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);

		OidcIdToken idToken = userRequest.getIdToken();
		// 여기서 회원가입 처리를 수행한다.
		Optional<MemberInfoDto> target = memberService.getMember(UserNameGenerator.of("Google", idToken.getEmail()));
		target.ifPresentOrElse(memberInfoDto -> {
			log.info("로그인 성공: {}", UserNameGenerator.of("Google", idToken.getEmail()));
		}, () -> {
			// 회원 가입 처리
			if (!idToken.getEmailVerified())
				throw new InvalidEmailRegistrationException("해당 이메일로는 가입할 수 없습니다.");
			MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
				.username(UserNameGenerator.of("Google", idToken.getEmail()))
				.nickname(idToken.getNickName())
				.profilePic(ImageDto.of(idToken.getPicture(), "google_profile_pics"))
				.build();
			memberService.join(memberRegisterDto);
		});

		return oidcUser;
	}
}
