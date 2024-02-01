package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class LogoutRequestDto {
	private final String token;
	private final String type;
}
