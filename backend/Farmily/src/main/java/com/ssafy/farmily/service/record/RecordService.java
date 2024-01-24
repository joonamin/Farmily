package com.ssafy.farmily.service.record;

import com.ssafy.farmily.dto.ChallengeRecordCheckRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPostRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPutRequestDto;
import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordPostRequestDto;
import com.ssafy.farmily.dto.EventRecordPutRequestDto;
import com.ssafy.farmily.dto.RecordResponseDto;

public interface RecordService {
	RecordResponseDto getById(long recordId);

	void createEventRecord(EventRecordPostRequestDto dto);

	void editEventRecord(EventRecordPutRequestDto dto);

	void createDailyRecord(DailyRecordPostRequestDto dto);
	void editDailyRecord(DailyRecordPutRequestDto dto);

	void createChallengeRecord(ChallengeRecordPostRequestDto dto);
	void checkChallengeRecord(ChallengeRecordCheckRequestDto dto);
	void editChallengeRecord(ChallengeRecordPutRequestDto dto);
}
