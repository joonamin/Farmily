package com.ssafy.farmily.service.record;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.aop.annotation.Statistics;
import com.ssafy.farmily.dto.ChallengeRecordMarkRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPostRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordPutRequestDto;
import com.ssafy.farmily.dto.ChallengeRecordResponseDto;
import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordPostRequestDto;
import com.ssafy.farmily.dto.EventRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordResponseDto;
import com.ssafy.farmily.dto.ImageCardRequestDto;
import com.ssafy.farmily.dto.RecordCommentDto;
import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.entity.ChallengeProgress;
import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Comment;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.ImageCard;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.ChallengeProgressRepository;
import com.ssafy.farmily.repository.CommentRepository;
import com.ssafy.farmily.repository.ImageCardRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.service.family.FamilyService;
import com.ssafy.farmily.service.file.FileService;
import com.ssafy.farmily.service.member.MemberService;
import com.ssafy.farmily.service.sprint.SprintService;
import com.ssafy.farmily.type.RecordType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
	private final RecordRepository recordRepository;
	private final CommentRepository commentRepository;
	private final ImageCardRepository imageCardRepository;
	private final ChallengeProgressRepository challengeProgressRepository;

	private final FileService fileService;
	private final SprintService sprintService;
	private final MemberService memberService;
	private final FamilyService familyService;

	@Override
	@Transactional
	public Record getEntityById(long recordId) {
		return recordRepository.findById(recordId)
			.orElseThrow(() -> new NoSuchContentException("기록이 없습니다."));
	}

	@Override
	@Transactional
	public RecordResponseDto getDtoById(long recordId) {
		Record entity = getEntityById(recordId);

		return switch (entity.getType()) {
			case DAILY -> RecordResponseDto.from(entity);
			case EVENT -> EventRecordResponseDto.from(entity);
			case CHALLENGE -> ChallengeRecordResponseDto.from((ChallengeRecord) entity);
		};
	}

	@Override
	@Statistics
	@Transactional
	public void createEventRecord(String username, EventRecordPostRequestDto dto) {
		Member member = memberService.getEntity(username);
		Sprint sprint = sprintService.getEntityById(dto.getSprintId());

		Record entity = Record.builder()
			.type(RecordType.EVENT)
			.sprint(sprint)
			.author(member)
			.title(dto.getTitle())
			.build();

		List<ImageCard> imageCards = persistImageCards(entity, dto.getImageCards());

		entity.setImageCards(imageCards);

		recordRepository.save(entity);
	}

	@Override
	@Transactional
	public void editEventRecord(String username, EventRecordPutRequestDto dto) {
		Record entity = recordRepository.findById(dto.getRecordId())
			.orElseThrow(NoSuchContentException::new);

		memberService.assertAuthorship(entity.getAuthor(), username);

		// TODO: 기존 파일 삭제
		List<ImageCard> imageCards = persistImageCards(entity, dto.getImageCards());

		entity.setTitle(dto.getTitle());
		entity.setImageCards(imageCards);

		recordRepository.save(entity);
	}

	@Override
	@Statistics
	@Transactional
	public void createDailyRecord(String username, DailyRecordPostRequestDto dto) {
		Member member = memberService.getEntity(username);
		Sprint sprint = sprintService.getEntityById(dto.getSprintId());

		Record entity = Record.builder()
			.type(RecordType.DAILY)
			.sprint(sprint)
			.author(member)
			.title(dto.getTitle())
			.content(dto.getContent())
			.build();

		recordRepository.save(entity);
	}

	@Override
	@Statistics
	@Transactional
	public void editDailyRecord(String username, DailyRecordPutRequestDto dto) {
		Record entity = getEntityById(dto.getRecordId());

		memberService.assertAuthorship(entity.getAuthor(), username);

		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());

		recordRepository.save(entity);
	}

	@Override
	@Transactional
	public void createChallengeRecord(String username, ChallengeRecordPostRequestDto dto) {
		Member member = memberService.getEntity(username);
		Sprint sprint = sprintService.getEntityById(dto.getSprintId());

		ChallengeRecord entity = ChallengeRecord.builder()
			.type(RecordType.CHALLENGE)
			.sprint(sprint)
			.author(member)
			.title(dto.getTitle())
			.content(dto.getContent())
			.dateRange(dto.getDateRange())
			.isRewarded(false)
			.progresses(List.of())
			.build();

		recordRepository.save(entity);
	}

	@Override
	@Statistics
	@Transactional
	public void markChallengeRecord(String username, ChallengeRecordMarkRequestDto dto) {
		Member member = memberService.getEntity(username);
		ChallengeRecord recordEntity = (ChallengeRecord) getEntityById(dto.getChallengeId());

		Long familyId = recordEntity.getSprint().getFamily().getId();
		familyService.assertMembership(familyId, username);

		ChallengeProgress progressEntity = ChallengeProgress.builder()
			.challenge(recordEntity)
			.date(LocalDate.now())
			.build();

		challengeProgressRepository.save(progressEntity);
	}

	@Override
	@Transactional
	public void editChallengeRecord(String username, ChallengeRecordPutRequestDto dto) {
		ChallengeRecord entity = (ChallengeRecord) getEntityById(dto.getRecordId());

		memberService.assertAuthorship(entity.getAuthor(), username);

		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());

		recordRepository.save(entity);
	}

	@Override
	public void createComment(Long recordId, String username, RecordCommentDto.Request.Post dto) {
		Record record = recordRepository.findById(recordId)
			.orElseThrow(NoSuchContentException::new);

		Member member = memberService.getEntity(username);

		System.out.println(member.getNickname());

		Comment comment = Comment.builder()
			.record(record)
			.author(member)
			.content(dto.getContent())
			.build();

		record.getComments().add(comment);

		recordRepository.save(record);
	}

	@Override
	public void editComment(Long recordId, Long commentId, String username, RecordCommentDto.Request.Put dto) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(NoSuchContentException::new);

		memberService.assertAuthorship(comment.getAuthor(), username);

		comment.setContent(dto.getContent());

		commentRepository.save(comment);
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
