package com.ssafy.farmily.validation.validator;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.annotation.NotIncludesNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotIncludesNullValidator
	implements ConstraintValidator<NotIncludesNull, DateRange>
{
	@Override
	public boolean isValid(DateRange dateRange, ConstraintValidatorContext constraintValidatorContext) {
		return check(dateRange);
	}

	public static boolean check(DateRange dateRange) {
		if (dateRange == null)
			return false;
		if (dateRange.getStartDate() == null)
			return false;
		if (dateRange.getEndDate() == null)
			return false;
		return true;
	}
}
