package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.utils.Position;
import com.ssafy.farmily.validation.validator.InRectangleValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link Position} 좌표가 직사각형 범위 내에 있는지 검사합니다.
 *
 * 직사각형의 경계를 모두 포함합니다.
 *
 * @author 구본웅
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InRectangleValidator.class)
public @interface InRectangle {
	String message() default "";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	int minRow();
	int minCol();
	int maxRow();
	int maxCol();
}
