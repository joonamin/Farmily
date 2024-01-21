package com.ssafy.farmily;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.AddressStandardClaim;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomOidcMemberService extends OidcUserService {

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);

		// ID Token 값을 찍어보자
		// TODO: payload가 어떻게 전달되는지 관찰
		OidcIdToken idToken = oidcUser.getIdToken();
		OidcUserInfo userInfo = oidcUser.getUserInfo();
		String userId = idToken.getSubject();
		String email = idToken.getEmail();

		// TODO: 여기서 회원가입 처리를 할 예정
		log.info("idToken: {}", idToken);
		log.info("userInfo: {}", userInfo);
		log.info("userId: {}", userId);
		log.info("email: {}", email);

		return oidcUser;
	}
}
