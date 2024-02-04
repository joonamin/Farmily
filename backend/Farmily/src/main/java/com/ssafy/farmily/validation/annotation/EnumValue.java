package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.validation.validator.ValueOfEnumValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface EnumValue {
	Class<? extends Enum<?>> enumClass();
	String message() default "유효하지 않은 Enum입니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	boolean ignoreCase() default false;
}
