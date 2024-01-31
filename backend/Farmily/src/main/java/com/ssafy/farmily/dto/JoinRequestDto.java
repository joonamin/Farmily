package com.ssafy.farmily.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JoinRequestDto {
	private String invitationCode;
	private String username;
}
