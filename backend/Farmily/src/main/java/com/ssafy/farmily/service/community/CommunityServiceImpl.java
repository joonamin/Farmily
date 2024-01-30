package com.ssafy.farmily.service.community;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.CommunityPostDetailDto;
import com.ssafy.farmily.dto.CommunityPostDto;
import com.ssafy.farmily.dto.InsertCommunityPostRequestDto;
import com.ssafy.farmily.entity.CommunityPost;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.CommunityPostRepository;

import lombok.RequiredArgsConstructor;

import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.service.file.FileService;
import com.ssafy.farmily.utils.SliceResponse;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
	private final CommunityPostRepository communityPostRepository;
	private final MemberRepository memberRepository;
	private final FileService fileService;

	@Override
	public SliceResponse<CommunityPostDto> getCommunityPostList(int size,int pageNum, Long lastSeenId) {
		PageRequest pageRequest = PageRequest.of(pageNum, size,Sort.by(Sort.Direction.DESC, "id"));

		Slice<CommunityPost> slice = communityPostRepository.findSliceBy(pageRequest);

		return SliceResponse.from(slice,CommunityPostDto::from);
	}

	@Override
	public String insertCommunityPost(InsertCommunityPostRequestDto requestDto, String username) {
		Member member = memberRepository.findByUsername(username).get();

		Image treeSnapshot = null;
		if(requestDto.getTreeSnapshot() != null){
			treeSnapshot = fileService.saveImage(requestDto.getTreeSnapshot());
		}

		CommunityPost communityPostDtoToToEntity =
			CommunityPost.builder()
				.title(requestDto.getTitle())
				.content(requestDto.getContent())
				.author(member)
				.treeImage(treeSnapshot).build();

		communityPostRepository.save(communityPostDtoToToEntity);

		return "Post Success";
	}

	@Override
	public CommunityPostDetailDto getPostDetail(Long postId) {
		CommunityPost entity = communityPostRepository.findById(postId).orElseThrow(() -> new NoSuchContentException("존재하지 않는 게시글입니다."));
		CommunityPostDetailDto communityPostDetailDto = CommunityPostDetailDto.from(entity);
		return communityPostDetailDto;
	}

}
