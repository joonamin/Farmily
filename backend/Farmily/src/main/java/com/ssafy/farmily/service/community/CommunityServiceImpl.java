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
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.CommunityPostRepository;

import lombok.RequiredArgsConstructor;

import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.service.family.FamilyService;
import com.ssafy.farmily.service.file.FileService;
import com.ssafy.farmily.service.member.MemberService;
import com.ssafy.farmily.utils.SliceResponse;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
	private final CommunityPostRepository communityPostRepository;

	private final MemberService memberService;
	private final FileService fileService;
	private final FamilyService familyService;

	@Override
	public SliceResponse<CommunityPostDto> getCommunityPostList(int size,int pageNum, Long lastSeenId) {
		PageRequest pageRequest = PageRequest.of(pageNum, size,Sort.by(Sort.Direction.DESC, "id"));

		Slice<CommunityPost> slice = communityPostRepository.findSliceBy(pageRequest);

		return SliceResponse.from(slice,CommunityPostDto::from);
	}

	@Override
	public void insertCommunityPost(InsertCommunityPostRequestDto requestDto, String username) {
		Member member = memberService.getEntity(username);

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
	}

	@Override
	public void putCommunityPost(InsertCommunityPostRequestDto requestDto, String username, Long communityPostId) {
		Member member = memberService.getEntity(username);
		CommunityPost communityPost = communityPostRepository.findById(communityPostId).orElseThrow(() -> new NoSuchContentException("존재하지 않는 게시글입니다."));

		if(member != communityPost.getAuthor()){
			throw new BusinessException("권한이 없습니다.");
		}

		communityPost.setTitle(requestDto.getTitle());
		communityPost.setContent(requestDto.getContent());
		Image treeSnapshot = null;
		if(requestDto.getTreeSnapshot() != null){
			treeSnapshot = fileService.saveImage(requestDto.getTreeSnapshot());
		}
		communityPost.setTreeImage(treeSnapshot);

		communityPostRepository.save(communityPost);
	}

	@Override
	public CommunityPostDetailDto getPostDetail(Long postId) {
		CommunityPost entity = communityPostRepository.findById(postId).orElseThrow(() -> new NoSuchContentException("존재하지 않는 게시글입니다."));
		CommunityPostDetailDto communityPostDetailDto = CommunityPostDetailDto.from(entity);
		return communityPostDetailDto;
	}
}
