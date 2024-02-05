package com.ssafy.farmily.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.farmily.aop.annotation.Statistics;
import com.ssafy.farmily.dto.ServiceProcessResult;
import com.ssafy.farmily.entity.FamilyStatistics;
import com.ssafy.farmily.repository.FamilyStatisticsRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class UpdateStatistics {

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
	}

	@AfterReturning(
		pointcut = "@annotation(com.ssafy.farmily.aop.annotation.Statistics)",
		returning = "result"
	)
	public void doUpdateStatistics(JoinPoint joinPoint, ServiceProcessResult result) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Statistics annotation = methodSignature.getMethod().getAnnotation(Statistics.class);

		FamilyStatistics.Field field = annotation.value();

		// 아래 부분은 ServiceProcessResult를 받아오는지 확인하기 위한 임시 코드입니다.
		if (result == null) {
			log.info("서비스의 리턴값이 ServiceProcessResult가 아닙니다.");
			return;
		} else {
			familyId = result.getFamilyId();
		}

		field.increment(familyStatisticsRepository, result.getFamilyId());

	}
}
