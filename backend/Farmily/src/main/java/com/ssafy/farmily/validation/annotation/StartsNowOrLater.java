package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.validator.StartsNowOrLaterValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link DateRange}가 {@link NotInverted}이고, 시작 일자가 현재 이후인지 검사합니다.
 *
 * @author 구본웅
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartsNowOrLaterValidator.class)
public @interface StartsNowOrLater {
	String message() default "기간의 시작 일자는 현재 일자 이후여야 합니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
