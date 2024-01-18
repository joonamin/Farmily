package com.ssafy.farmily.type;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
public class DateRange {
	private LocalDate startDate;
	private LocalDate endDate;

	/**
	 * 시작일자와 종료일자 사이 기간을 구합니다.
	 * 시작일을 기산하지 않습니다. 즉, 시작일과 종료일이 같으면 0일을 반환합니다.
	 * @return 시작일자와 종료일자 사이의 기간
	 */
	public Period getPeriod() {
		return Period.between(startDate, endDate);
	}
}