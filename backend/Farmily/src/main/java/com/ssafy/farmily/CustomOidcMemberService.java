package com.ssafy.farmily;

import java.util.Optional;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.ImageDto;
import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.exception.InvalidEmailRegistrationException;
import com.ssafy.farmily.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import utils.UserNameGenerator;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOidcMemberService extends OidcUserService {

	private final MemberService memberService;

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);

		OidcIdToken idToken = userRequest.getIdToken();
		// 여기서 회원가입 처리를 수행한다.
		Optional<MemberInfoDto> target = memberService.getMember(UserNameGenerator.of("Google", idToken.getEmail()));
		target.ifPresentOrElse(this::login, () -> join(idToken));

		return oidcUser;
	}

	private void login(MemberInfoDto memberInfoDto) {
		// todo: 추후에 jwt 관련 발급을 여기서 처리한다.
		log.info("로그인 성공: {}", UserNameGenerator.of("Google", memberInfoDto.getUsername()));
	}

	private void join(OidcIdToken idToken) {
		if (Boolean.FALSE.equals(idToken.getEmailVerified()))
			throw new InvalidEmailRegistrationException("해당 이메일로는 가입할 수 없습니다.");
		MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
			.username(UserNameGenerator.of("Google", idToken.getEmail()))
			.nickname(idToken.getNickName())
			.profilePic(ImageDto.of(idToken.getPicture(), "google_profile_pics"))
			.build();
		memberService.join(memberRegisterDto);
	}
}
