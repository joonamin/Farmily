package com.ssafy.farmily.dto;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.CommunityPost;
import com.ssafy.farmily.entity.Image;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommunityPostDetailDto {
	String title;
	String content;
	String author;
	Image treeImage;

	public static CommunityPostDetailDto from(CommunityPost communityPost) {
		CommunityPostDetailDto postDetailDto = new CommunityPostDetailDto();
		BeanUtils.copyProperties(communityPost, postDetailDto);
		return postDetailDto;
	}
}
