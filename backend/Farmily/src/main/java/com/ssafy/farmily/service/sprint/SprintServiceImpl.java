package com.ssafy.farmily.service.sprint;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.ImageCardImageDto;
import com.ssafy.farmily.dto.RecordBriefResponseDto;
import com.ssafy.farmily.dto.SprintRecordCountsDto;
import com.ssafy.farmily.dto.SprintRecordFirstResponseDto;
import com.ssafy.farmily.dto.SprintRecordPageResponseDto;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.ImageRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.repository.SprintRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

	private final SprintRepository sprintRepository;
	private final ImageRepository imageRepository;
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
	public SprintRecordFirstResponseDto getRecordsInitially(Long sprintId, int pageSize, int imageCountMax) {
		Sprint sprint = this.getEntityById(sprintId);

		SprintRecordCountsDto countsDto
			= SprintRecordCountsDto.from(recordRepository.countRecordGroupByRecordType(sprintId));

		return SprintRecordFirstResponseDto.builder()
			.counts(countsDto)
			.dateRange(sprint.getDateRange())
			.images(this.getRandomImageCardImageDtos(sprintId, imageCountMax))
			.page(this.getRecordsPagination(sprint, 1, pageSize))
			.build();
	}

	@Override
	public SprintRecordPageResponseDto getRecordsPagination(Long sprintId, int pageNo, int pageSize) {
		Sprint sprint = this.getEntityById(sprintId);

		return this.getRecordsPagination(sprint, pageNo, pageSize);
	}

	private List<ImageCardImageDto> getRandomImageCardImageDtos(Long sprintId, int countMax) {
		int imageTotalCount = imageRepository.countAllImagesInSprint(sprintId);

		List<ImageCardImageDto> imageCardImageDtos;
		if (imageTotalCount <= countMax) {
			imageCardImageDtos
				= imageRepository.findAllImageCardImageDtosInSprintOrderByIdDesc(sprintId);
		}
		else {
			imageCardImageDtos
				= imageRepository.findAllImageCardImageDtosInSprintAndIdInOrderByIdDesc(sprintId, countMax);
		}

		return imageCardImageDtos;
	}

	private SprintRecordPageResponseDto getRecordsPagination(Sprint sprint, int pageNo, int pageSize) {
		List<RecordBriefResponseDto> challenges = recordRepository.findCurrentChallenges(sprint.getFamily().getId())
			.stream()
			.map(RecordBriefResponseDto::from)
			.toList();

		int challengeCount = challenges.size();

		int recordPageSize = pageSize - challengeCount;

		// PageRequest의 pageNumber는 0부터 시작
		int zeroBasedPageNo = pageNo - 1;

		Pageable pageRequest = PageRequest.of(zeroBasedPageNo, recordPageSize, Sort.Direction.DESC, "id");

		Page<Record> recordPage = recordRepository.findAllBySprintOrderByIdDesc(sprint, pageRequest);

		List<RecordBriefResponseDto> records = null;
		if (pageNo > recordPage.getTotalPages()) {
			records = List.of();
		}
		else {
			records = recordPage.stream()
				.map(RecordBriefResponseDto::from)
				.toList();
		}

		return SprintRecordPageResponseDto.builder()
			.challenges(challenges)
			.records(records)
			.pageTotal(recordPage.getTotalPages())
			.build();
	}
}
