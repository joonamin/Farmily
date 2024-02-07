package com.ssafy.farmily.validation.validator;

import com.ssafy.farmily.dto.FamilyFruitSkinsDto;
import com.ssafy.farmily.type.Item;
import com.ssafy.farmily.validation.annotation.NoDuplicatedFruitSkins;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoDuplicatedFruitSkinsValidator
	implements ConstraintValidator<NoDuplicatedFruitSkins, FamilyFruitSkinsDto>
{
	@Override
	public boolean isValid(
		FamilyFruitSkinsDto familyFruitSkinsDto,
		ConstraintValidatorContext constraintValidatorContext
	) {
		Item daily = familyFruitSkinsDto.getDaily();
		Item challenge = familyFruitSkinsDto.getChallenge();
		Item event = familyFruitSkinsDto.getEvent();

		return daily != challenge && challenge != event && event != daily;
	}
}
