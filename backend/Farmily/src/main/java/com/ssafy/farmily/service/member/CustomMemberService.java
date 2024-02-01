package com.ssafy.farmily.service.member;

import java.security.Key;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.LogoutRequestDto;
import com.ssafy.farmily.dto.MemberEditRequestDto;
import com.ssafy.farmily.dto.MemberInfoDto;
import com.ssafy.farmily.dto.MemberRegisterDto;
import com.ssafy.farmily.entity.Image;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.entity.Token;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.exception.ForbiddenException;
import com.ssafy.farmily.repository.MemberRepository;
import com.ssafy.farmily.repository.TokenBlackListRepository;
import com.ssafy.farmily.utils.JwtUtils;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomMemberService implements MemberService {

	private final MemberRepository memberRepository;
	private final TokenBlackListRepository tokenBlackListRepository;
	private final Environment env;

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
			.orElseThrow(() -> new NoSuchElementException("인증이 성공했지만 Member를 찾을 수 없습니다."));
	}

	@Override
	public LogoutResponseDto logout(LogoutRequestDto logoutRequest) {
		Token token = Token.from(logoutRequest);
		tokenBlackListRepository.findByToken(token.getToken())
			.ifPresentOrElse(t -> {
				throw new BusinessException("이미 로그아웃된 토큰입니다.");
			}, () -> tokenBlackListRepository.save(token));

		String salt = env.getProperty("jwt.secret", String.class);
		Key key = Keys.hmacShaKeyFor(salt.getBytes());

		return LogoutResponseDto.builder()
			.username(JwtUtils.getUsernameFromToken(logoutRequest.getToken(), key))
			.expiredToken(logoutRequest.getToken())
			.build();
	}

	@Override
	public void assertAuthorship(Member authorEntity, String username) {
		if (authorEntity == null)
			throw new RuntimeException("작성자 확인 실패: 작성자가 null입니다.");
		if (!Objects.equals(authorEntity.getUsername(), username))
			throw new ForbiddenException();
	}

	@Override
	@Transactional
	public void editMemberInfo(String username, MemberEditRequestDto dto) {
		Member member = this.getEntity(username);

		member.setNickname(dto.getNewNickname());

		memberRepository.save(member);
	}
}
