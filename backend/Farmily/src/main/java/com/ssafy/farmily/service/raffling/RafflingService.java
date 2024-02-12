package com.ssafy.farmily.service.raffling;

import com.ssafy.farmily.dto.RafflingRequestDto;
import com.ssafy.farmily.dto.RafflingResponseDto;

public interface RafflingService {
	public RafflingResponseDto raffleItem(RafflingRequestDto dto, String username);
}
