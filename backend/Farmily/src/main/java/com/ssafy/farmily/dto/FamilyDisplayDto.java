package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDisplayDto {

	private Long familyId;
	private String name;
	private String motto;
	private String profileImageUrl;
}
