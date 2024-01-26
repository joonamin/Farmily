package com.ssafy.farmily.service.record;

import com.ssafy.farmily.dto.ChallengeRecordMarkRequestDto;

import com.ssafy.farmily.dto.ChallengeRecordPostRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPutRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordResponseDto;
import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordPostRequestDto;
import com.ssafy.farmily.dto.EventRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordResponseDto;
import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.entity.Record;

public interface RecordService {
	Record getEntityById(long recordId);
	RecordResponseDto getDtoById(long recordId);
	EventRecordResponseDto getEventDtoById(long recordId);
	ChallengeRecordResponseDto getChallengeDtoById(long recordId);

	void createEventRecord(EventRecordPostRequestDto dto);

	void editEventRecord(EventRecordPutRequestDto dto);

	void createDailyRecord(DailyRecordPostRequestDto dto);
	void editDailyRecord(DailyRecordPutRequestDto dto);

	void createChallengeRecord(ChallengeRecordPostRequestDto dto);

	void markChallengeRecord(ChallengeRecordMarkRequestDto dto);

	void editChallengeRecord(ChallengeRecordPutRequestDto dto);
}
