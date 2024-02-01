package com.ssafy.farmily.entity;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RedisHash
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Conference {
	@Id
	private Long id;

	private String sessionId;
}
