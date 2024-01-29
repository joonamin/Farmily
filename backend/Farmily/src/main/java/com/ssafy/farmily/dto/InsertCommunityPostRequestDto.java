package com.ssafy.farmily.dto;

import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Sprint;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InsertCommunityPostRequestDto {
	@NotNull
	String title;

	@NotNull
	String content;

	// TODO: Entity가 아닌 타입을 참조
	Member author;

	// TODO: Entity가 아닌 타입을 참조
	Image treeSnapshot;
}
