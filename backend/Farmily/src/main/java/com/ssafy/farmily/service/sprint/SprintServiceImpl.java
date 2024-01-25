package com.ssafy.farmily.service.sprint;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

	private final SprintRepository sprintRepository;
	private final RecordRepository recordRepository;

	@Override
	public Sprint getSprint(Long id) {
		return sprintRepository.findById(id)
			.orElseThrow(() -> new NoSuchContentException("스프린트가 없습니다."));
	}

	@Override
	public void harvest(Long sprintId) {

	}

	@Override
	public List<RecordResponseDto> getRecords(Long sprintId) {
		Sprint sprint = getSprint(sprintId);

		List<Record> entities = recordRepository.findAllBySprint(sprint);

		List<RecordResponseDto> dtos = entities.stream().map(RecordResponseDto::from).toList();

		return dtos;
	}
}
