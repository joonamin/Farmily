package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.validator.NotInvertedValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link DateRange}가 {@link NotIncludesNull}이고, 종료 일자가 시작 일자보다 과거가 아닌지 검사합니다.
 *
 * @author 구본웅
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotInvertedValidator.class)
public @interface NotInverted {
	String message() default "기간의 시작 일자는 종료 일자 이전이여야 합니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
