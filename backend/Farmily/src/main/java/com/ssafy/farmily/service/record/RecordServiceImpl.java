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

		Record entity = Record.builder()
			.sprint(null)	// TODO: sprint 연결, validation 및 적용
			.author(null)	// TODO: UserPrincipal 연결, validation 및 적용
			.title(dto.getTitle())
			.content(dto.getContent())
			.build();

		recordRepository.save(entity);
	}
}
