package com.ssafy.farmily.service.member;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.exception.ForbiddenException;
import com.ssafy.farmily.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomMemberService implements MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public MemberInfoDto join(MemberRegisterDto memberRegisterDto) {
		Image googleProfilePic = Image.builder().location(memberRegisterDto.getProfilePic().getLocation())
			.originalFileName(memberRegisterDto.getProfilePic().getOriginalFileName()).build();
		Member member = Member.builder()
			.username(memberRegisterDto.getUsername())
			.password(memberRegisterDto.getPassword())
			.nickname(memberRegisterDto.getNickname())
			.profilePic(googleProfilePic)
			.build();
		Member savedMember = memberRepository.save(member);
		return MemberInfoDto.from(savedMember);
	}

	@Override
	public Optional<MemberInfoDto> getMember(String username) {
		Optional<Member> target = memberRepository.findByUsername(username);
		return target.map(MemberInfoDto::from);
	}

	@Override
	public Member getEntity(String username) {
		return memberRepository.findByUsername(username)
			.orElseThrow(() -> new RuntimeException("인증이 성공했지만 Member를 찾을 수 없습니다."));
	}

	@Override
	public void assertAuthorship(Member authorEntity, String username) {
		if (authorEntity == null)
			throw new RuntimeException("작성자 확인 실패: 작성자가 null입니다.");
		if (!Objects.equals(authorEntity.getUsername(), username))
			throw new ForbiddenException();
	}
}
