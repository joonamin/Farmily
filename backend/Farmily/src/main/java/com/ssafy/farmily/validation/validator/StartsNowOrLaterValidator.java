package com.ssafy.farmily.validation.validator;

import java.time.LocalDate;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.annotation.StartsNowOrLater;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class StartsNowOrLaterValidator
	implements ConstraintValidator<StartsNowOrLater, DateRange>
{
	@Override
	public boolean isValid(DateRange dateRange, ConstraintValidatorContext constraintValidatorContext) {
		return check(dateRange);
	}

	public static boolean check(DateRange dateRange) {
		if (!NotIncludesNullValidator.check(dateRange))
			return false;

		LocalDate now = LocalDate.now();
		return !dateRange.getStartDate().isBefore(now);
	}
}
