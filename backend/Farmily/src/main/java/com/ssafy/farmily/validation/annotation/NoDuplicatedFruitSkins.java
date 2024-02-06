package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.validation.validator.InRectangleValidator;
import com.ssafy.farmily.validation.validator.NoDuplicatedFruitSkinsValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link com.ssafy.farmily.dto.FamilyFruitSkinsDto}의 각 값들이 중복되지 않는지 검사합니다.
 *
 * @author 구본웅
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoDuplicatedFruitSkinsValidator.class)
public @interface NoDuplicatedFruitSkins {
	String message() default "열매 스킨들 중 중복이 없어야 합니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
