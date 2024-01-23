package com.ssafy.farmily.service.record;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.ChallengeRecordPostRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPutRequestDto;
import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordPostRequestDto;
import com.ssafy.farmily.dto.EventRecordPutRequestDto;
import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.type.RecordType;
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
	public void createEventRecord(EventRecordPostRequestDto dto) {

		// TODO: Multipart 이미지 업로드 및 테이블 저장 (FileService에 위임)

		Record entity = Record.builder()
			.type(RecordType.EVENT)
			.sprint(null)	// TODO: sprint 연결 및 적용
			.author(null)	// TODO: UserPrincipal 연결 및 적용
			.title(dto.getTitle())
			.imageCards(List.of())	// TODO: 업로드한 이미지 Entity 불러오기
			.build();

		recordRepository.save(entity);
	}

	@Override
	public void editEventRecord(EventRecordPutRequestDto dto) {
		Record entity = recordRepository.findById(dto.getRecordId())
			.orElseThrow(NoSuchContentException::new);

		// TODO: 기존 파일 삭제
		// TODO: 새로운 파일 업로드

		entity.setTitle(dto.getTitle());
		entity.setImageCards(List.of());	// TODO: 이미지 적용하기

		recordRepository.save(entity);
	}

	@Override
	@Transactional
	public void createDailyRecord(DailyRecordPostRequestDto dto) {
		Record entity = Record.builder()
			.type(RecordType.DAILY)
			.sprint(null)	// TODO: sprint 연결 및 적용
			.author(null)	// TODO: UserPrincipal 연결 및 적용
			.title(dto.getTitle())
			.content(dto.getContent())
			.build();

		recordRepository.save(entity);
	}

	@Override
	public void editDailyRecord(DailyRecordPutRequestDto dto) {
		Record entity = recordRepository.findById(dto.getRecordId())
			.orElseThrow(NoSuchContentException::new);

		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());

		recordRepository.save(entity);
	}

	@Override
	public void createChallengeRecord(ChallengeRecordPostRequestDto dto) {
	}

	@Override
	public void editChallengeRecord(ChallengeRecordPutRequestDto dto) {

	}
}
