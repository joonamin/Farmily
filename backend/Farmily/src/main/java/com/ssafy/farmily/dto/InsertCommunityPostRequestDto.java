package com.ssafy.farmily.dto;

import org.springframework.web.multipart.MultipartFile;

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
	String author;
	MultipartFile treeSnapshot;
}
