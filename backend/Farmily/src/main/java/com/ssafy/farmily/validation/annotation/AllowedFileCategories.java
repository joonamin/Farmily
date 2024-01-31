package com.ssafy.farmily.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.type.FileCategory;
import com.ssafy.farmily.validation.validator.AllowedFileCategoriesValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * {@link MultipartFile} 파일의 확장자가 특정 카테고리에 포함되는지 검사합니다.
 *
 * @author 구본웅
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedFileCategoriesValidator.class)
public @interface AllowedFileCategories {
	String message() default "";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	FileCategory[] categories();
}
