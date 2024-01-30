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
	// 현재 상황 :
	// 4의 size로 4개를 불러와서 다음 content가 있으면 hasNext를 true로 하고 (4,3,2,1)
	// 마지막 한 놈을 짤라서 3개를 출력 (4,3,2)
	// getOffset이 size * pageNum으로 다음 페이지가 짤린 한 놈부터 시작을 해야되는데 (1,0)
	// 전 페이지에서 (4,3,2,1) 다 들고 왔다고 인식하고 다음 페이지는 (0)부터 출력
	// 그럼 query를 slice로 받지말고 SELECT * FROM community_post WHERE id > 6(lastSeenId) LIMIT 4; 이런 예시로
	// 인자를 달리하고 받아온 리스트를 Slice하자고
	@Override
	public SliceResponse<CommunityPostDto> getCommunityPostList(int size,int pageNum, Long lastSeenId) {
		PageRequest pageRequest = PageRequest.of(pageNum, size,Sort.by(Sort.Direction.DESC, "id"));

		Slice<CommunityPost> slice = communityPostRepository.findSliceBy(pageRequest);

		return SliceResponse.from(slice,CommunityPostDto::from);
	}

	@Override
	public String insertCommunityPost(InsertCommunityPostRequestDto requestDto) {
		Image treeSnapshot = null;
		if(requestDto.getTreeSnapshot() != null){
			treeSnapshot = fileService.saveImage(requestDto.getTreeSnapshot());
		}

		CommunityPost communityPostDtoToToEntity =
			CommunityPost.builder()
				.title(requestDto.getTitle())
				.content(requestDto.getContent())
				// TODO : Community 게시글 글쓴이를 얻어오는 로직 필요 >> Oauth를 통해 Member정보를 얻어와야 될 듯
				// memberRepository.findByUsername(Oauth.username)
				.author(memberRepository.findByUsername(requestDto.getAuthor()).get())
				.treeImage(treeSnapshot).build();

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
