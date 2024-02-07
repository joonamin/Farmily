package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.type.ItemType;
import com.ssafy.farmily.validation.validator.AllowedItemTypeValidator;
import com.ssafy.farmily.validation.validator.NoDuplicatedFruitSkinsValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link com.ssafy.farmily.type.Item}의 타입이 특정 {@link ItemType} 중 하나에 속하는지 확인합니다.
 *
 * @author 구본웅
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedItemTypeValidator.class)
public @interface AllowedItemType {
	String message() default "허용되지 않은 타입의 아이템입니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	ItemType[] itemTypes();
}
