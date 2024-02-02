package com.ssafy.farmily.service.sprint;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.ImageDto;
import com.ssafy.farmily.dto.SprintRecordFirstResponseDto;
import com.ssafy.farmily.dto.SprintRecordPageResponseDto;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.ImageRepository;
import com.ssafy.farmily.repository.SprintRepository;
import com.ssafy.farmily.utils.RandomNumberGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService {

	private final SprintRepository sprintRepository;
	private final ImageRepository imageRepository;

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

		return SprintRecordFirstResponseDto.builder()
			.dateRange(sprint.getDateRange())
			.images(this.getRandomImages(sprintId, imageCountMax))
			.page(this.getRecordsPagination(sprint, 1, pageSize))
			.build();
	}

	@Override
	public SprintRecordPageResponseDto getRecordsPagination(Long sprintId, int pageNo, int pageSize) {
		Sprint sprint = this.getEntityById(sprintId);

		return this.getRecordsPagination(sprint, pageNo, pageSize);
	}

	private List<ImageDto> getRandomImages(Long sprintId, int countMax) {
		int imageTotalCount = imageRepository.countAllImagesInSprint(sprintId);

		List<Image> ret;
		if (imageTotalCount <= countMax) {
			ret = imageRepository.findAllImagesInSprintOrderByIdDesc(sprintId);
		}
		else {
			Set<Long> indexes = RandomNumberGenerator.getRandomUniqueLongs(0, imageTotalCount, countMax);
			ret = imageRepository.findAllImagesInSprintAndIdInOrderByIdDesc(sprintId, indexes);
		}

		return ret.stream().map(ImageDto::from).toList();
	}

	private SprintRecordPageResponseDto getRecordsPagination(Sprint sprint, int pageNo, int pageSize) {

	}
}
