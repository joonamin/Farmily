package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.utils.DateRange;
import com.ssafy.farmily.validation.validator.NotIncludesNullValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link DateRange}가 {@code null}이 아니고, {@code null}을 포함하지 않는지 검사합니다.
 *
 * @author 구본웅
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotIncludesNullValidator.class)
public @interface NotIncludesNull {
	String message() default "기간의 시작 일자와 종료 일자는 null이 아니어야 합니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
