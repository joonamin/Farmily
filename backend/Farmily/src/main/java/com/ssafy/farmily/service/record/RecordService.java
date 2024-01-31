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

	void createEventRecord(String username, EventRecordPostRequestDto dto);

	void editEventRecord(String username, EventRecordPutRequestDto dto);

	void createDailyRecord(String username, DailyRecordPostRequestDto dto);
	void editDailyRecord(String username, DailyRecordPutRequestDto dto);

	void createChallengeRecord(String username, ChallengeRecordPostRequestDto dto);

	void markChallengeRecord(String username, ChallengeRecordMarkRequestDto dto);

	void editChallengeRecord(String username, ChallengeRecordPutRequestDto dto);
}
