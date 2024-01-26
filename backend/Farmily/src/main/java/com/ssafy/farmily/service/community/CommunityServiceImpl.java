package com.ssafy.farmily.service.community;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.CommunityPostDetailDto;
import com.ssafy.farmily.dto.CommunityPostDto;
import com.ssafy.farmily.dto.InsertCommunityPostRequestDto;
import com.ssafy.farmily.entity.CommunityPost;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.repository.CommunityPostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
	private final CommunityPostRepository communityPostRepository;


	@Override
	public Slice<CommunityPostDto> getCommunityPostList(int size) {
		PageRequest pageRequest = PageRequest.of(0, size+1,Sort.by(Sort.Direction.DESC, "id"));

		Slice<CommunityPost> slices = communityPostRepository.findSliceBy(pageRequest);
		// id,title,author, content, sprint
		// id, title, author, content, sprintId, image
		List<CommunityPostDto> content = new ArrayList<>();
		for(CommunityPost communityPost : slices){
			content.add(new CommunityPostDto().from(communityPost));
		}
		boolean hasNext = false;
		if(content.size() > pageRequest.getPageSize()){
			content.remove(pageRequest.getPageSize());
			hasNext = true;
		}
		return new SliceImpl<>(content,pageRequest,hasNext);
	}

	@Override
	public String insertCommunityPost(InsertCommunityPostRequestDto requestDto) {
		CommunityPost communityPostDtoToToEntity =
			CommunityPost.builder()
				.title(requestDto.getTitle())
				.content(requestDto.getContent())
				.author(requestDto.getAuthor())
				.treeImage(requestDto.getTreeSnapshot()).build();

		communityPostRepository.save(communityPostDtoToToEntity);

		return "Post Success";
	}

	@Override
	public CommunityPostDetailDto getPostDetail(Long postId) {
		CommunityPost entity = communityPostRepository.findById(postId).get();
		CommunityPostDetailDto communityPostDetailDto = CommunityPostDetailDto.from(entity);
		return communityPostDetailDto;
	}
}
