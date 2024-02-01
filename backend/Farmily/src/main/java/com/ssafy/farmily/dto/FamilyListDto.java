package com.ssafy.farmily.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FamilyListDto {
	private List<FamilyInfo> families;

	@AllArgsConstructor
	public static class FamilyInfo {
		Long familyId;
		String familyName;
	}
}
