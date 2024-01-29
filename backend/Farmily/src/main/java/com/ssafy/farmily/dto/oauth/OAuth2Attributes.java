package com.ssafy.farmily.dto.oauth;

import java.util.Map;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.ssafy.farmily.utils.UserNameGenerator;

/**
 * 해당 클래스는 OAuth2Provider로부터 받은 claims을 파싱하여
 * Member 클래스에 적합한 정보를 구성하는 클래스입니다.
 */

@Getter
@RequiredArgsConstructor
@Builder
public class OAuth2Attributes {

	@AllArgsConstructor
	public enum Provider {
		GOOGLE("google", "GOOGLE_", OAuth2Attributes::ofGoogle),
		KAKAO("kakao", "Kakao_", OAuth2Attributes::ofKakao),

		FACEBOOK("facebook", "Facebook_", OAuth2Attributes::ofFacebook);

		private final String name;
		private final String prefix;
		private final Function<Map<String, Object>, OAuth2Attributes> resolver;
	}

	static {
		PROVIDER_MAP = Map.of(
			"google", Provider.GOOGLE,
			"kakao", Provider.KAKAO,
			"facebook", Provider.FACEBOOK
		);
	}

	private static final Map<String, Provider> PROVIDER_MAP;
	private static final String DEFAULT_NICKNAME = "사용자 닉네임";
	private static final String DEFAULT_PROFILE_PIC = "사용자 프로필 사진 URL";

	private final String username;
	private final String nickname;
	private final String profilePic;

	public static OAuth2Attributes of(String registrationId, Map<String, Object> attributes) {
		return PROVIDER_MAP.get(registrationId).resolver.apply(attributes);
	}

	private static OAuth2Attributes ofGoogle(Map<String, Object> attributes) {
		String username = UserNameGenerator.of(Provider.GOOGLE.name(), ((String)attributes.get("sub")).substring(0, 10));
		String nickname = (String)attributes.getOrDefault("nickname", DEFAULT_NICKNAME);
		String picture = (String)attributes.get("picture");

		return OAuth2Attributes.builder()
			.username(username)
			.nickname(nickname)
			.profilePic(picture)
			.build();
	}

	private static OAuth2Attributes ofKakao(Map<String, Object> attributes) {
		String username = UserNameGenerator.of(Provider.KAKAO.name(), ((String)attributes.get("sub")).substring(0, 10));
		String nickname = (String)attributes.getOrDefault("nickname", DEFAULT_NICKNAME);
		String picture = (String)attributes.getOrDefault("picture", DEFAULT_PROFILE_PIC);

		return OAuth2Attributes.builder()
			.username(username)
			.nickname(nickname)
			.profilePic(picture)
			.build();
	}

	private static OAuth2Attributes ofFacebook(Map<String, Object> attributes) {
		String username = UserNameGenerator.of(Provider.FACEBOOK.name(), ((String)attributes.get("id")).substring(0, 10));
		String nickname = (String)attributes.getOrDefault("nickname", DEFAULT_NICKNAME);
		String picture = (String)attributes.getOrDefault("picture.data.url", DEFAULT_PROFILE_PIC);

		return OAuth2Attributes.builder()
			.username(username)
			.nickname(nickname)
			.profilePic(picture)
			.build();
	}

}
