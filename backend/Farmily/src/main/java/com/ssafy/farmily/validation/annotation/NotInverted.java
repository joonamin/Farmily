package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.validator.StartsNowOrLaterValidator;

import jakarta.validation.Constraint;

/**
 * {@link DateRange}의 종료 일자가 시작 일자보다 과거가 아닌지 검사합니다.
 *
 * @author 구본웅
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartsNowOrLaterValidator.class)
public @interface NotInverted {
}
