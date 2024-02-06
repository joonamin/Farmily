package com.ssafy.farmily.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class FamilyPatchRequestDto {
	private FamilyPatchRequestDto() {}

	@Getter
	@Setter
	@RequiredArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Name {
		@NotNull
		private String newName;
	}

	@Getter
	@Setter
	@RequiredArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Motto {
		@NotNull
		private String newMotto;
	}
}
