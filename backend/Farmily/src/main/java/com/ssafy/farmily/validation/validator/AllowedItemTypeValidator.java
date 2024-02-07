package com.ssafy.farmily.validation.validator;

import java.util.Arrays;

import com.ssafy.farmily.type.Item;
import com.ssafy.farmily.type.ItemType;
import com.ssafy.farmily.validation.annotation.AllowedItemType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedItemTypeValidator
	implements ConstraintValidator<AllowedItemType, Item>
{
	private ItemType[] allowedTypes;

	@Override
	public void initialize(AllowedItemType annotation) {
		this.allowedTypes = annotation.itemTypes();
	}

	@Override
	public boolean isValid(Item item, ConstraintValidatorContext constraintValidatorContext) {
		return Arrays.stream(allowedTypes).anyMatch(allowedType -> allowedType == item.getType());
	}
}
