package com.ssafy.farmily.dto;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Sprint;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InsertCommunityPostRequestDto {
	String title;
	String content;
	String author;
	MultipartFile treeSnapshot;
}
