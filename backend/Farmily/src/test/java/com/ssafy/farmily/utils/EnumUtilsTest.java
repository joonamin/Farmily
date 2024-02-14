package com.ssafy.farmily.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.farmily.dto.OpenViduWebhookEventDto;

@SpringBootTest
@ActiveProfiles("local")
public class EnumUtilsTest {
	@Test
	public void EnumUtils_테스트() {
		String camelCase = "sessionCreated";
		OpenViduWebhookEventDto.EventType eventType
			= EnumUtils.fromCamelCase(OpenViduWebhookEventDto.EventType.class, camelCase);
		Assertions.assertEquals(
			OpenViduWebhookEventDto.EventType.SESSION_CREATED,
			eventType
		);
	}
}
