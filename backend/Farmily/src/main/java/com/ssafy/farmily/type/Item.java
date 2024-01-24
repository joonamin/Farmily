package com.ssafy.farmily.type;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Item {
	TREE_1(ItemType.TREE_SKIN),
	TREE_2(ItemType.ACCESSORY);

	@NonNull
	private final ItemType type;
}