package com.ssafy.farmily.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.farmily.entity.CommunityPost;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.service.file.FileService;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommunityPostDetailDto {
	String title;
	String content;
	String author;
	ImageDto treeImage;

	public static CommunityPostDetailDto from(CommunityPost communityPost) {
		CommunityPostDetailDto postDetailDto = new CommunityPostDetailDto();
		BeanUtils.copyProperties(communityPost, postDetailDto);
		ImageDto treeSnapshot = ImageDto.from(communityPost.getTreeImage());
		postDetailDto.setTreeImage(treeSnapshot);
		postDetailDto.setAuthor(communityPost.getAuthor().getNickname());
		return postDetailDto;
	}
}
