package com.ssafy.farmily.validation.validator;

import java.time.LocalDate;

import com.ssafy.farmily.validation.annotation.StartsNowOrLater;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import utils.DateRange;

/**
 * {@code {@link DateRange}}의 시작 일자가 현재 이후인지 검사합니다.
 * 
 * @author 구본웅
 */
public class StartsNowOrLaterValidator
	implements ConstraintValidator<StartsNowOrLater, DateRange>
{
	@Override
	public boolean isValid(DateRange dateRange, ConstraintValidatorContext constraintValidatorContext) {
		LocalDate now = LocalDate.now();
		return !dateRange.getStartDate().isBefore(now);
	}
}
