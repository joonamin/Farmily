package com.ssafy.farmily.validation.validator;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.annotation.NotInverted;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class NotInvertedValidator
	implements ConstraintValidator<NotInverted, DateRange>
{
	@Override
	public boolean isValid(DateRange dateRange, ConstraintValidatorContext constraintValidatorContext) {
		return check(dateRange);
	}

	public static boolean check(DateRange dateRange) {
		if (!NotIncludesNullValidator.check(dateRange))
			return false;
		return !dateRange.getPeriod().isNegative();
	}
}
