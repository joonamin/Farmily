package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.FamilyFruitSkins;
import com.ssafy.farmily.type.Item;

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
public class FamilyFruitSkinsDto {
	private Item daily;
	private Item challenge;
	private Item event;

	public static FamilyFruitSkinsDto from(FamilyFruitSkins entity) {
		return new FamilyFruitSkinsDto(entity.getDaily(), entity.getChallenge(), entity.getEvent());
	}
}
