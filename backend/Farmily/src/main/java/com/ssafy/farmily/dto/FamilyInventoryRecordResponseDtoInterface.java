package com.ssafy.farmily.dto;

import com.ssafy.farmily.type.RecordType;

public interface FamilyInventoryRecordResponseDtoInterface {
	Long getId();

	RecordType getType();

	String getTitle();
}
