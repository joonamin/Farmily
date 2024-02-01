package com.ssafy.farmily.service.family;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.FamilyDisplayDto;
import com.ssafy.farmily.entity.FamilyMembership;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.repository.FamilyMembershipRepository;
import com.ssafy.farmily.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilyMembershipService {

	private final MemberRepository memberRepository;
	private final FamilyMembershipRepository familyMembershipRepository;

	public List<FamilyDisplayDto> getFamilies(String username) {
		List<FamilyDisplayDto> userFamilies = new ArrayList<>();
		Member member = memberRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

		List<FamilyMembership> familyMemberships = familyMembershipRepository.findByMemberId(member.getId());
		familyMemberships.forEach(fms -> userFamilies.add(FamilyDisplayDto.builder()
			.familyId(fms.getFamily().getId())
			.name(fms.getFamily().getName())
			.motto(fms.getFamily().getMotto())
			.profileImageUrl(fms.getFamily().getImage().getLocation())
			.build()));
		return userFamilies;
	}
}
