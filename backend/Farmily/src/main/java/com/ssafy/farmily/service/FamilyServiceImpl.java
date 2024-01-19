package com.ssafy.farmily.service;

import java.security.Provider;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.FamilyMainDTO;
import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.RecordRepository;


@Service
public class FamilyServiceImpl implements FamilyService{
	@Autowired
	FamilyRepository familyRepository;
	@Autowired
	RecordRepository recordRepository;
	@Override
	public FamilyMainDTO familyMain(Long familyId) {
		Optional<Family> temp = familyRepository.findById(familyId);
		FamilyMainDTO familyMainDTO = null;
		if(temp.isPresent()){
			Family family = temp.get();
			familyMainDTO = FamilyMainDTO.of(family);
			List<Long> temp2 = recordRepository.findCurrentChallenges(familyId);
			familyMainDTO.setChallengesIds(temp2);
		}
		return familyMainDTO;
	}
}
