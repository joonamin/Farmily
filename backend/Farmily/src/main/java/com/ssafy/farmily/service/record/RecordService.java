package com.ssafy.farmily.service.record;

import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.RecordResponseDto;

public interface RecordService {
	RecordResponseDto getById(long recordId);

	void createDaily(DailyRecordPostRequestDto dto);
	void editDaily(DailyRecordPutRequestDto dto);
}
