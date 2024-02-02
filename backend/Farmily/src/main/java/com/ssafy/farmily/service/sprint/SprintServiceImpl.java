package com.ssafy.farmily.service.sprint;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.SprintRecordFirstResponseDto;
import com.ssafy.farmily.dto.SprintRecordPageResponseDto;
import com.ssafy.farmily.entity.Member;
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
	@Transactional
	public Sprint getEntityById(Long sprintId) {
		return sprintRepository.findById(sprintId)
			.orElseThrow(() -> new NoSuchContentException("스프린트가 없습니다."));
	}

	@Override
	@Transactional
	public void harvest(Long sprintId) {
		Sprint sprint = getEntityById(sprintId);

		sprint.setIsHarvested(true);

		sprint.getFamily().getTree().getPlacements().clear();

		sprintRepository.save(sprint);
	}

	@Override
	public SprintRecordFirstResponseDto getRecordsInitially(Long sprintId, int pageSize) {
		return null; // TODO
	}

	@Override
	public SprintRecordPageResponseDto getRecordsPagination(Long sprintId, int pageNo, int pageSize) {
		return null; // TODO
	}
}
