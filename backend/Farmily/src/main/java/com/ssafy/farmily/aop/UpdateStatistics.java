package com.ssafy.farmily.aop;

import java.util.Arrays;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.ServiceProcessResult;
import com.ssafy.farmily.repository.FamilyStatisticsRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class UpdateStatistics {
	private final String[] SUPPORTED_PREFIXES = {"post", "mark", "create", "swap"};
	private Map<String, Runnable> handlers;

	private Long familyId = 1L;
	private final FamilyStatisticsRepository familyStatisticsRepository;

	@Autowired
	public UpdateStatistics(FamilyStatisticsRepository familyStatisticsRepository) {
		this.familyStatisticsRepository = familyStatisticsRepository;
	}

	@PostConstruct
	void initialize() {
		// todo: familyId를 메소드로 부터 얻어오기
		// create 관련 응답값을 void로 두지 않으면 좋겠습니다 -> void로 두면 업데이트 관련 메타 정보를 받아올 수 없습니다.
		// ServiceProcessResult 를 extends 하여 Response를 작성해주시면 감사하겠습니다.
		// 현재는 임시로 1로 설정
		handlers = Map.ofEntries(
			Map.entry("family", () -> log.info("family statistics update")),
			Map.entry("sprint", () -> log.info("sprint statistics update")),
			Map.entry("item", () -> log.info("item statistics update")),
			Map.entry("achievement", () -> log.info("achievement statistics update"))
		);
	}

	@AfterReturning(pointcut = "@annotation(com.ssafy.farmily.aop.annotation.Statistics)", returning = "result")
	public void doUpdateStatistics(JoinPoint joinPoint, ServiceProcessResult result) {
		String name = joinPoint.getSignature().getName();
		// 아래 부분은 ServiceProcessResult를 받아오는지 확인하기 위한 임시 코드입니다.
		if (result == null) {
			log.info("service process return value is Void");
		} else {
			familyId = result.getFamilyId();
		}

		if (isSupported(name)) {
			throw new UnsupportedOperationException("지원하지 않는 메소드입니다. statistics 업데이트 관련 메소드에만 사용할 수 있습니다.");
		}
		// signature.getName()을 이용해서 두번째 word를 이용하여 가져온 다음
		// 타입에 맞게 업데이트 시킨다.
		String[] args = parseTypes(name);
		if (handlers.containsKey(args[1])) {
			handlers.get(args[1]).run();
		} else {
			throw new UnsupportedOperationException("지원하지 않는 메소드입니다. statistics 업데이트 관련 메소드에만 사용할 수 있습니다.");
		}
		log.info("update family statistics after => {}", name);

	}

	// 이 메소드는 family_statistics 테이블에 매핑되는 타입인지 검사하는 로직이 포함된다.
	private boolean isSupported(String type) throws UnsupportedOperationException {
		String[] args = parseTypes(type);
		if (args.length < 2) {
			throw new UnsupportedOperationException("잘못된 시그니처가 전달되었습니다. camelCase로 작성된 메소드 이름인지 확인해주세요");
		}
		return Arrays.stream(SUPPORTED_PREFIXES).anyMatch(prefix -> args[0].equals(prefix));
	}

	private String[] parseTypes(String name) {
		String[] words = name.split("(?=[A-Z])");
		return Arrays.stream(words).map(String::toLowerCase).toArray(String[]::new);
	}

	// transactional 이 동작하려면 public 으로 둘 수 밖에 없었습니다. 혹시 더 괜찮은 방법을 알고 계신다면 알려주세요
	@Transactional
	public void incrementCalendarPlanCount() {
		familyStatisticsRepository.incrementCalendarPlanCount(familyId);
	}

	@Transactional
	public void incrementChallengeCompleteCount() {
		familyStatisticsRepository.incrementChallengeCompleteCount(familyId);
	}

	@Transactional
	public void incrementDailyRecordCount() {
		familyStatisticsRepository.incrementDailyRecordCount(familyId);
	}

	@Transactional
	public void incrementEventRecordCount() {
		familyStatisticsRepository.incrementEventRecordCount(familyId);
	}

	@Transactional
	public void incrementHarvestCount() {
		familyStatisticsRepository.incrementHarvestCount(familyId);
	}

}
