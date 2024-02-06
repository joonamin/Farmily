package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.FamilyFruitSkins;
import com.ssafy.farmily.type.Item;
import com.ssafy.farmily.type.ItemType;
import com.ssafy.farmily.validation.annotation.AllowedItemType;
import com.ssafy.farmily.validation.annotation.NoDuplicatedFruitSkins;

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
	@AllowedItemType(itemTypes = ItemType.ACCESSORY)
	private Item daily;

	@AllowedItemType(itemTypes = ItemType.ACCESSORY)
	private Item challenge;

	@AllowedItemType(itemTypes = ItemType.ACCESSORY)
	private Item event;


	public static FamilyFruitSkinsDto DEFAULT = new FamilyFruitSkinsDto(
		Item.ALPHABET_A, Item.ALPHABET_B, Item.ALPHABET_C
	);

	public static FamilyFruitSkinsDto from(FamilyFruitSkins entity) {
		return new FamilyFruitSkinsDto(entity.getDaily(), entity.getChallenge(), entity.getEvent());
	}
}
