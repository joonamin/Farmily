package com.ssafy.farmily.type;

import java.util.List;
import java.util.function.Function;

import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyItem;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Item {
	ALPHABET_A(ItemType.ACCESSORY),
	ALPHABET_B(ItemType.ACCESSORY),
	ALPHABET_C(ItemType.ACCESSORY),
	ALPHABET_D(ItemType.ACCESSORY),
	ALPHABET_E(ItemType.ACCESSORY),
	ALPHABET_F(ItemType.ACCESSORY),
	ALPHABET_G(ItemType.ACCESSORY),
	ALPHABET_H(ItemType.ACCESSORY),
	ALPHABET_I(ItemType.ACCESSORY),
	ALPHABET_J(ItemType.ACCESSORY),
	ALPHABET_K(ItemType.ACCESSORY),
	ALPHABET_L(ItemType.ACCESSORY),
	ALPHABET_M(ItemType.ACCESSORY),
	ALPHABET_N(ItemType.ACCESSORY),
	ALPHABET_O(ItemType.ACCESSORY),
	ALPHABET_P(ItemType.ACCESSORY),
	ALPHABET_Q(ItemType.ACCESSORY),
	ALPHABET_R(ItemType.ACCESSORY),
	ALPHABET_S(ItemType.ACCESSORY),
	ALPHABET_T(ItemType.ACCESSORY),
	ALPHABET_U(ItemType.ACCESSORY);

	@NonNull
	private final ItemType type;
}