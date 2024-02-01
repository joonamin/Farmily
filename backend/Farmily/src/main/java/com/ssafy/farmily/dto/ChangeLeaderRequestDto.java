package com.ssafy.farmily.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChangeLeaderRequestDto {
	private Long newLeaderMemberId;
}
