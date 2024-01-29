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

	// 현재 상황 :
	// 4의 size로 4개를 불러와서 다음 content가 있으면 hasNext를 true로 하고 (4,3,2,1)
	// 마지막 한 놈을 짤라서 3개를 출력 (4,3,2)
	// getOffset이 size * pageNum으로 다음 페이지가 짤린 한 놈부터 시작을 해야되는데 (1,0)
	// 전 페이지에서 (4,3,2,1) 다 들고 왔다고 인식하고 다음 페이지는 (0)부터 출력
	// 그럼 query를 slice로 받지말고 SELECT * FROM community_post WHERE id > 6(lastSeenId) LIMIT 4; 이런 예시로
	// 인자를 달리하고 받아온 리스트를 Slice하자고
	@Override
	public Slice<CommunityPostDto> getCommunityPostList(int size,int pageNum, Long lastSeenId) {
		PageRequest pageRequest = PageRequest.of(pageNum, size,Sort.by(Sort.Direction.DESC, "id"));
		List<CommunityPost> slices = null;
		if(pageNum == 0L){
			slices = communityPostRepository.locaSliceFirstList(size+1);
		}
		else{
			slices = communityPostRepository.loadSliceList(lastSeenId,size+1);
		}

		// id,title,author, content, sprint
		// id, title, author, content, sprintId, image
		List<CommunityPostDto> content = new ArrayList<>();
		for(CommunityPost communityPost : slices){
			content.add(new CommunityPostDto().from(communityPost));
		}
		boolean hasNext = false;
		if(content.size() > size){
			content.remove(size);
			hasNext = true;
		}
		System.out.println("@@@@@@@@@@@@@@@@@" + hasNext);
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
