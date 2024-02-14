package com.ssafy.farmily.service.record;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import com.ssafy.farmily.dto.ChallengeRewardRequestDto;
import com.ssafy.farmily.dto.DailyRecordPostRequestDto;
import com.ssafy.farmily.dto.DailyRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordPostRequestDto;
import com.ssafy.farmily.dto.EventRecordPutRequestDto;
import com.ssafy.farmily.dto.EventRecordResponseDto;
import com.ssafy.farmily.dto.ImageCardRequestDto;
import com.ssafy.farmily.dto.RecordCommentDto;
import com.ssafy.farmily.dto.RecordResponseDto;
import com.ssafy.farmily.dto.ServiceProcessResult;
import com.ssafy.farmily.entity.ChallengeProgress;
import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Comment;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyStatistics;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.ImageCard;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Record;
import com.ssafy.farmily.entity.Sprint;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.ChallengeProgressRepository;
import com.ssafy.farmily.repository.CommentRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.ImageCardRepository;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.repository.RecordRepository;
import com.ssafy.farmily.service.family.FamilyService;
import com.ssafy.farmily.service.file.FileService;
import com.ssafy.farmily.service.member.MemberService;
import com.ssafy.farmily.service.sprint.SprintService;
import com.ssafy.farmily.type.RecordType;
import com.ssafy.farmily.utils.DateRange;

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
			case CHALLENGE -> ChallengeRecordResponseDto.from((ChallengeRecord)entity);
		};
	}

	@Override
	@Statistics(FamilyStatistics.Field.EVENT_RECORD_COUNT)
	@Transactional
	public ServiceProcessResult createEventRecord(String username, EventRecordPostRequestDto dto) {
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

		return new ServiceProcessResult(dto.getFamilyId());
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
	@Statistics(FamilyStatistics.Field.DAILY_RECORD_COUNT)
	@Transactional
	public ServiceProcessResult createDailyRecord(String username, DailyRecordPostRequestDto dto) {
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

		return new ServiceProcessResult(dto.getFamilyId());
	}

	@Override
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
	@Transactional
	public void markChallengeRecord(String username, ChallengeRecordMarkRequestDto dto) {
		Member member = memberService.getEntity(username);
		ChallengeRecord recordEntity = (ChallengeRecord)getEntityById(dto.getChallengeId());

		if (challengeProgressRepository.existsByDateAndId(LocalDate.now(), dto.getChallengeId()))
			throw new BusinessException("이미 체크 된 날짜입니다.");

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
		ChallengeRecord entity = (ChallengeRecord)getEntityById(dto.getRecordId());

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
		for (int i = 0; i < images.length; i++) {
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

	@Override
	@Transactional
	@Statistics(FamilyStatistics.Field.CHALLENGE_COMPLETE_COUNT)
	public ServiceProcessResult setChallengeComplete(String username, Long recordId, ChallengeRewardRequestDto dto) {
		familyService.assertMembership(dto.getFamilyId(), username);

		ChallengeRecordResponseDto recordDto = (ChallengeRecordResponseDto)getDtoById(recordId);

		if (!checkComplete(recordDto.getDateRange(), recordDto.getProgresses())) {
			throw new BusinessException("챌린지가 달성되지 않았습니다.");
		}
		if (recordDto.getIsRewarded()){
			throw new BusinessException("이미 보상을 획득한 챌린지입니다.");
		}
		ChallengeRecord challengeRecord = (ChallengeRecord)recordRepository.findById(recordId).orElseThrow(()->new BusinessException("유효하지 않은 챌린지 ID"));
		challengeRecord.setIsRewarded(true);
		recordRepository.save(challengeRecord);

		return new ServiceProcessResult(dto.getFamilyId());
	}

	@Override
	public void deleteRecord(String username, Long recordId) {
		Record record = recordRepository.findById(recordId).orElseThrow(()-> new BusinessException("존재하지 않는 글입니다."));
		Family family = record.getSprint().getFamily();
		familyService.assertMembership(family.getId(), username);

		recordRepository.delete(record);
	}

	private boolean checkComplete(DateRange dateRange, List<LocalDate> progress) {
		long duration = dateRange.getStartDate().until(dateRange.getEndDate(), ChronoUnit.DAYS);
		long completeDays = progress.size();
		double percent = completeDays / (double)duration;
		return percent >= 0.7;
	}


}
