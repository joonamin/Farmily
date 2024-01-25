package com.ssafy.farmily.dto;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.CommunityPost;
import com.ssafy.farmily.entity.Sprint;

public class CommunityPostDto {
	Long id;
	String title;
	String author;
	String content;
	Long sprintId;
	ImageDto image;

	public CommunityPostDto from(CommunityPost post){
		CommunityPostDto communityPostDto = new CommunityPostDto();
		BeanUtils.copyProperties(post,communityPostDto);
		return communityPostDto;
	}
}
