package com.ssafy.farmily.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.ImageDto;
import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
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
		if (target.isPresent()) {
			return Optional.of(MemberInfoDto.from(target.get()));
		} else {
			return Optional.empty();
		}
	}
}
