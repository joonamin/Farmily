package com.ssafy.farmily.service.sprint;

import java.util.List;

import com.ssafy.farmily.dto.RecordBriefResponseDto;
import com.ssafy.farmily.dto.SprintRecordFirstResponseDto;
import com.ssafy.farmily.dto.SprintRecordPageResponseDto;
import com.ssafy.farmily.entity.Sprint;

public interface SprintService {
	Sprint getEntityById(Long sprintId);

	void harvest(Long sprintId);

	SprintRecordFirstResponseDto getRecordsInitially(Long sprintId, int pageSize, int imageCountMax);
	SprintRecordPageResponseDto getRecordsPagination(Long sprintId, int pageNo, int pageSize);
}
