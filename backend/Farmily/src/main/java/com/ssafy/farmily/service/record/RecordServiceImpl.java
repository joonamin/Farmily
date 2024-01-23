package com.ssafy.farmily.service.record;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.RecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
	private final RecordRepository recordRepository;

	@Override
	@Transactional
	public RecordResponseDto getById(long recordId) {
		Record entity = recordRepository.findById(recordId)
			.orElseThrow(NoSuchContentException::new);

		return RecordResponseDto.from(entity);
	}

	@Override
	@Transactional
	public void createDaily(DailyRecordPostRequestDto dto) {
		/*
		Sprint sprint = recordRepository.findById(dto.getSprintId())
			.orElseThrow(() -> new)
		 */

		Record entity = Record.builder()
			.sprint(null)	// TODO: sprint 연결 및 적용
			.author(null)	// TODO: UserPrincipal 연결 및 적용
			.title(dto.getTitle())
			.content(dto.getContent())
			.build();

		recordRepository.save(entity);
	}

	@Override
	public void editDaily(DailyRecordPutRequestDto dto) {
		Record entity = recordRepository.findById(dto.getRecordId())
			.orElseThrow(NoSuchContentException::new);

		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());

		recordRepository.save(entity);
	}
}
