package com.ssafy.farmily.validation.validator;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.validation.annotation.AllowedFileCategories;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedFileCategoriesValidator
	implements ConstraintValidator<AllowedFileCategories, MultipartFile>
{
	private Set<String> allowedExtensions;

	@Override
	public void initialize(AllowedFileCategories annotation) {
		allowedExtensions = Arrays.stream(annotation.categories())
			.flatMap(fileCategory -> Arrays.stream(fileCategory.getExtensions()))
			.collect(Collectors.toSet());
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
		if (Objects.isNull(multipartFile))
			return true;
		String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		return allowedExtensions.contains(extension);
	}
}
