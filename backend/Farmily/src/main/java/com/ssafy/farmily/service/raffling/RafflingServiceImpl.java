package com.ssafy.farmily.service.raffling;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.farmily.dto.RafflingRequestDto;
import com.ssafy.farmily.dto.RafflingResponseDto;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.entity.FamilyItem;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.repository.FamilyItemRepository;
import com.ssafy.farmily.repository.FamilyRepository;
import com.ssafy.farmily.service.family.FamilyService;
import com.ssafy.farmily.type.Item;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RafflingServiceImpl implements RafflingService {
	private final FamilyRepository familyRepository;
	private final FamilyItemRepository familyItemRepository;

	private final FamilyService familyService;
	private final int RAFFLING_COST = 100;

	@Override
	@Transactional
	public RafflingResponseDto raffleItem(RafflingRequestDto dto, String username) {
		Long familyId = dto.getFamilyId();
		Family family = familyService.getEntity(familyId);
		familyService.assertMembership(familyId, username);

		List<FamilyItem> collectedItem = familyItemRepository.findAllByFamilyId(familyId);
		Item[] allOfItemList = Item.values();

		int familyPoint = family.getPoint();
		RafflingResponseDto responseDto = new RafflingResponseDto();
		if (familyPoint < RAFFLING_COST) {
			throw new BusinessException("포인트가 부족합니다.");
		}
		if (collectedItem.size() == allOfItemList.length) {
			throw new BusinessException("이미 모든 아이템을 수집했습니다.");
		}
		familyPoint -= RAFFLING_COST;
		family.setPoint(familyPoint);
		responseDto.setFamilyPoint(familyPoint);
		familyRepository.save(family);

		boolean IsDuplication = true;
		while (IsDuplication) {
			int rafflingItemId = (int)(Math.random() * allOfItemList.length);
			Item item = allOfItemList[rafflingItemId];

			if (!familyItemRepository.existsByCodeAndFamilyId(item, familyId)) {
				FamilyItem entity = FamilyItem.builder()
					.family(family)
					.code(item)
					.type(item.getType())
					.build();
				familyItemRepository.save(entity);
				responseDto.setRafflingCode(String.valueOf(item));
				IsDuplication = false;
			}
		}
		return responseDto;
	}
}
