package com.ssafy.farmily.utils;

import org.springframework.util.StringUtils;

import com.ssafy.farmily.dto.oauth.LoginResponseDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtils {

	private CookieUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static void addOAuth2TokenToBrowser(HttpServletResponse res, LoginResponseDto tokenResponse) {
		if (StringUtils.hasText(tokenResponse.getAccessToken())) {
			addCookie(res, "accessToken", tokenResponse.getAccessToken(), 60 * 15 * 60);
		} else {
			throw new IllegalArgumentException("accessToken이 존재하지 않음");
		}
		if (StringUtils.hasText(tokenResponse.getRefreshToken())) {
			addCookie(res, "refreshToken", tokenResponse.getRefreshToken(), 60 * 60 * 24 * 14); // 2주
		} else {
			throw new IllegalArgumentException("refreshToken이 존재하지 않음");
		}
	}

	private static void addCookie(HttpServletResponse res, String name, String value, int maxAge) {
		// secure옵션은 dev환경에서는 적용해제
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		// cookie.setSecure(true);
		// cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge);
		res.addCookie(cookie);
	}
}
