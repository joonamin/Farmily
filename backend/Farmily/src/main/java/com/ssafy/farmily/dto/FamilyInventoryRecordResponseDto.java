package com.ssafy.farmily.dto;

import com.ssafy.farmily.type.RecordType;

import lombok.Data;

@Data
public class FamilyInventoryRecordResponseDto {
	private Long id;
	private RecordType type;
	private String title;

	public static FamilyInventoryRecordResponseDto of(
		FamilyInventoryRecordResponseDtoInterface dtoInterface
	) {
		FamilyInventoryRecordResponseDto dto = new FamilyInventoryRecordResponseDto();
		dto.id = dtoInterface.getId();
		dto.type = dtoInterface.getType();
		dto.title = dtoInterface.getTitle();

		return dto;
	}
}
