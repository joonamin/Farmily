package com.ssafy.farmily.service;

import java.security.Provider;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.FamilyItemDTO;
import com.ssafy.farmily.dto.FamilyMainDTO;
import com.ssafy.farmily.entity.ChallengeRecord;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.exception.NotFoundFamilyId;
import com.ssafy.farmily.repository.FamilyItemRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.repository.RecordRepository;

@Service
public class FamilyServiceImpl implements FamilyService {
	@Autowired
	FamilyRepository familyRepository;
	@Autowired
	RecordRepository recordRepository;
	@Autowired
	FamilyItemRepository familyItemRepository;

	@Override
	public FamilyMainDTO familyMain(Long familyId) {
		Optional<Family> temp = familyRepository.findById(familyId);
		FamilyMainDTO familyMainDTO = null;
		if (temp.isPresent()) {
			Family family = temp.get();
			familyMainDTO = FamilyMainDTO.of(family);
			List<Long> temp2 = recordRepository.findCurrentChallenges(familyId);
			familyMainDTO.setChallengesIds(temp2);
		} else {
			throw new NotFoundFamilyId();
		}
		return familyMainDTO;
	}

	@Override
	public List<FamilyItemDTO> familyInventory(Long familyId) {
		Optional<Family> temp = familyRepository.findById(familyId);
		// 받아올 때 추가가 많이 발생할 것 같아서 LinkedList
		List<FamilyItemDTO> familyItemDtoList = new LinkedList<>();
		if (temp.isPresent()) {
			List<FamilyItem> temp2 = familyItemRepository.findByFamilyId(familyId);

			for (FamilyItem item : temp2) {
				FamilyItemDTO familyItemDTO = new FamilyItemDTO().of(item);
				familyItemDtoList.add(familyItemDTO);
			}
		} else {
			throw new NotFoundFamilyId();
		}
		return familyItemDtoList;
	}
}
