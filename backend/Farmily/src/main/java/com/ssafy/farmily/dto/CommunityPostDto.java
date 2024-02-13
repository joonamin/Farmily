package com.ssafy.farmily.dto;

import org.springframework.beans.BeanUtils;

import com.ssafy.farmily.entity.CommunityPost;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommunityPostDto {
	Long id;
	String title;
	String author;
	String content;
	ImageDto image;

	public static CommunityPostDto from(CommunityPost post) {
		CommunityPostDto communityPostDto = new CommunityPostDto();
		communityPostDto.id = post.getId();
		communityPostDto.author = post.getAuthor().getNickname();
		communityPostDto.title = post.getTitle();
		communityPostDto.content = post.getContent();
		communityPostDto.image = ImageDto.from(post.getTreeImage());
		return communityPostDto;
	}
}
