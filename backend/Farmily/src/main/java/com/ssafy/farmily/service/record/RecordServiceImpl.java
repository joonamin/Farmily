package com.ssafy.farmily.service.record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.dto.ChallengeRecordMarkRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPostRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPutRequestDto;
import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordPostRequestDto;
import com.ssafy.farmily.dto.EventRecordPutRequestDto;
import com.ssafy.farmily.dto.ImageCardRequestDto;
import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.entity.ChallengeProgress;
import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.ImageCard;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.type.RecordType;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.ImageCardRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.service.file.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
	private final RecordRepository recordRepository;
	private final ImageCardRepository imageCardRepository;

	private final FileService fileService;

	@Override
	@Transactional
	public RecordResponseDto getById(long recordId) {
		Record entity = recordRepository.findById(recordId)
			.orElseThrow(NoSuchContentException::new);

		return RecordResponseDto.from(entity);
	}

	@Override
	@Transactional
	public void createEventRecord(EventRecordPostRequestDto dto) {
		Record entity = Record.builder()
			.type(RecordType.EVENT)
			.sprint(null)	// TODO: sprint 연결 및 적용
			.author(null)	// TODO: UserPrincipal 연결 및 적용
			.title(dto.getTitle())
			.build();

		List<ImageCard> imageCards = persistImageCards(entity, dto.getImageCards());

		entity.setImageCards(imageCards);

		recordRepository.save(entity);
	}

	@Override
	@Transactional
	public void editEventRecord(EventRecordPutRequestDto dto) {
		Record entity = recordRepository.findById(dto.getRecordId())
			.orElseThrow(NoSuchContentException::new);

		// TODO: 기존 파일 삭제
		List<ImageCard> imageCards = persistImageCards(entity, dto.getImageCards());

		entity.setTitle(dto.getTitle());
		entity.setImageCards(imageCards);	// TODO: 이미지 적용하기

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
	@Transactional
	public void editDailyRecord(DailyRecordPutRequestDto dto) {
		Record entity = recordRepository.findById(dto.getRecordId())
			.orElseThrow(NoSuchContentException::new);

		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());

		recordRepository.save(entity);
	}

	@Override
	@Transactional
	public void createChallengeRecord(ChallengeRecordPostRequestDto dto) {
		ChallengeRecord entity = ChallengeRecord.builder()
			.type(RecordType.CHALLENGE)
			.sprint(null)
			.author(null)
			.title(dto.getTitle())
			.content(dto.getContent())
			.dateRange(dto.getDateRange())
			.isRewarded(false)
			.progresses(List.of())
			.build();

		recordRepository.save(entity);
	}

	@Override
	public void markChallengeRecord(ChallengeRecordMarkRequestDto dto) {
		ChallengeRecord recordEntity = (ChallengeRecord) recordRepository.findById(dto.getChallengeId())
				.orElseThrow(NoSuchContentException::new);

		ChallengeProgress progressEntity = ChallengeProgress.builder()
			.challenge(recordEntity)
			.date(dto.getDate())
			.build();

		recordEntity.getProgresses().add(progressEntity);

		recordRepository.save(recordEntity);
	}

	@Override
	@Transactional
	public void editChallengeRecord(ChallengeRecordPutRequestDto dto) {
		ChallengeRecord entity = (ChallengeRecord) recordRepository.findById(dto.getRecordId())
			.orElseThrow(NoSuchContentException::new);

		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());
		entity.setDateRange(dto.getDateRange());

		recordRepository.save(entity);
	}


	private List<ImageCard> persistImageCards(Record record, Collection<ImageCardRequestDto> dtos) {
		List<MultipartFile> multipartFiles = dtos.stream()
			.map(ImageCardRequestDto::getImageFile)
			.toList();

		Image[] images = fileService.saveImages(multipartFiles).toArray(new Image[0]);

		String[] descriptions = dtos.stream().map(ImageCardRequestDto::getDescription)
			.toList().toArray(new String[0]);

		List<ImageCard> imageCards = new ArrayList<>();
		for (int i=0; i<images.length; i++) {
			ImageCard imageCard = ImageCard.builder()
				.image(images[i])
				.description(descriptions[i])
				.record(record)
				.build();

			imageCardRepository.save(imageCard);
			imageCards.add(imageCard);
		}

		return imageCards;
	}
}
