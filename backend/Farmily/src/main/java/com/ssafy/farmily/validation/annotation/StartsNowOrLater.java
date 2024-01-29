package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.validation.validator.StartsNowOrLaterValidator;

import jakarta.validation.Constraint;
import utils.DateRange;

/**
 * {@code {@link DateRange }}의 시작 일자가 현재 이후인지 검사합니다.
 *
 * @author 구본웅
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartsNowOrLaterValidator.class)
public @interface StartsNowOrLater {

}
