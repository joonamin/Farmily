package com.ssafy.farmily.service.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.DailyRecordRequestDto;
import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.RecordRepository;

@Service
public class RecordServiceImpl implements RecordService {
	@Autowired
	RecordRepository recordRepository;

	@Override
	public RecordResponseDto get(long recordId) {
		Record entity = recordRepository.findById(recordId)
			.orElseThrow(NoSuchContentException::new);

		RecordResponseDto dto = RecordResponseDto.from(entity);

		return dto;
	}

	@Override
	public void createDaily(DailyRecordRequestDto dto) {
		/*
		Sprint sprint = recordRepository.findById(dto.getSprintId())
			.orElseThrow(() -> new)
		 */
	}
}
