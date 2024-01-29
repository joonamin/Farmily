package com.ssafy.farmily.validation.validator;

import java.time.LocalDate;

import com.ssafy.farmily.validation.annotation.NotInverted;
import com.ssafy.farmily.validation.annotation.StartsNowOrLater;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import utils.DateRange;


public class NotInvertedValidator
	implements ConstraintValidator<NotInverted, DateRange>
{
	@Override
	public boolean isValid(DateRange dateRange, ConstraintValidatorContext constraintValidatorContext) {
		return !dateRange.getPeriod().isNegative();
	}
}
