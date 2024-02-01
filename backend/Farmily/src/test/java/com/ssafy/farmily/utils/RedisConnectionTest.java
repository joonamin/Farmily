package com.ssafy.farmily.utils;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.farmily.dto.LogoutRequestDto;
import com.ssafy.farmily.entity.Token;
import com.ssafy.farmily.repository.TokenBlackListRepository;
import com.ssafy.farmily.service.member.LogoutResponseDto;
import com.ssafy.farmily.service.member.MemberService;

@SpringBootTest
@ActiveProfiles(value = "local")
class RedisConnectionTest {

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@Autowired
	TokenBlackListRepository tokenBlackListRepository;

	@Autowired
	MemberService memberService;

	@BeforeEach
	void beforeEach() {
		tokenBlackListRepository.deleteAll(tokenBlackListRepository.findAll());
	}

	@Test
	void redis_connection_test() {
		assertThat(redisTemplate).isNotNull();
		assertThat(redisTemplate.getConnectionFactory().getConnection().isClosed()).isFalse();
		redisTemplate.opsForValue().set("test", "test");
		assertThat(redisTemplate.opsForValue().get("test")).isEqualTo("test");
		assertThat(redisTemplate.delete("test")).isTrue();
		assertThat(redisTemplate.opsForValue().get("test")).isNull();
	}

	@Test
	void member_logout_redis_register() throws InterruptedException {
		String permanentToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYXJtaWx5IiwiaWF0IjoxNzA2NzA5MTMzLCJleHAiOjE4NjQ0NzU1MzMsImF1ZCI6ImZvclRlc3QiLCJzdWIiOiJPSURDX1RFU1QiLCJyb2xlcyI6IlVTRVIifQ.9PXWrZy4t3OtSLDN-oOAlBg5rfKdraLiwiBQtKnVK9M";
		LogoutRequestDto logoutRequestDto = new LogoutRequestDto(permanentToken, "access");
		LogoutResponseDto logoutResponseDto = memberService.logout(logoutRequestDto);
		assertThat(logoutResponseDto).isNotNull();
		assertThat(logoutResponseDto.getExpiredToken()).isEqualTo(logoutRequestDto.getToken());

		assertThat(tokenBlackListRepository.findByToken(logoutRequestDto.getToken())).isPresent();
	}

}
