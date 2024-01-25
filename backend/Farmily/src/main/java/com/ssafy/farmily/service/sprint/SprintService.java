package com.ssafy.farmily.service.sprint;

import java.util.List;

import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.entity.Sprint;

public interface SprintService {
	Sprint getSprint(Long id);

	void harvest(Long sprintId);

	List<RecordResponseDto> getRecords(Long sprintId);
}
