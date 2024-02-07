package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.FamilyFruitSkins;
import com.ssafy.farmily.type.Item;
import com.ssafy.farmily.type.ItemType;
import com.ssafy.farmily.validation.annotation.AllowedItemType;
import com.ssafy.farmily.validation.annotation.NoDuplicatedFruitSkins;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@NoDuplicatedFruitSkins
public class FamilyFruitSkinsDto {
	@NotNull
	@AllowedItemType(itemTypes = ItemType.ACCESSORY)
	private Item daily;

	@NotNull
	@AllowedItemType(itemTypes = ItemType.ACCESSORY)
	private Item challenge;

	@NotNull
	@AllowedItemType(itemTypes = ItemType.ACCESSORY)
	private Item event;

	public static FamilyFruitSkinsDto from(FamilyFruitSkins entity) {
		return FamilyFruitSkinsDto.builder()
			.daily(entity.getDaily())
			.challenge(entity.getChallenge())
			.event(entity.getEvent())
			.build();
	}
}
