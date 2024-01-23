package com.ssafy.farmily.controller.family;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.common.Message;
import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.service.family.FamilyService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/family")
@Slf4j
@RequiredArgsConstructor
public class FamilyController {
	private final FamilyService familyService;

	// 그래서 준비했다, lombok!!!


	// 요청 시 메인 인덱스에 DB에 저장된 가족정보를 가져옴
	@SneakyThrows
	@GetMapping("/{familyId}")
	public ResponseEntity<Message> mainIndex(@PathVariable Long familyId) {
		Message message = new Message();
		FamilyMainDto familyMainDTO = familyService.setMainFamilyInfo(familyId);
		message.setMessage("가족 메인 정보");
		message.setData(familyMainDTO);

		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/{familyId}/inventory")
	public ResponseEntity<Message> inventory(@PathVariable Long familyId){
		Message message = new Message();
		List<FamilyItemDto> familyItemDtoList = familyService.getFamilyInventory(familyId);

		message.setMessage("인벤토리 정보");
		message.setData(familyItemDtoList);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@GetMapping("/{familyId}/basket")
	public ResponseEntity<Message> getFamilyBasketList(@PathVariable Long familyId){
		Message message = new Message();
		List<FamilyBasketDto> familyBasketDTOList = familyService.getFamilySprintList(familyId);
		message.setMessage("바구니 목록");
		message.setData(familyBasketDTOList);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	/*
	{
    "treeId":,
    "placementDtoList":[
        {
        "dtype":"",
        "position":{
            "row":,
            "col":
        },
        "recordId":
        }
    ]
}
	 */
	@Transactional
	@PostMapping("/placement")
	public ResponseEntity<Message> itemPlacement(@RequestBody PlacingItemRequestDto placementList){

		log.info("placementList: {}",placementList);
		Message message = new Message();
		String result = familyService.placingItems(placementList);
		message.setMessage("배치 저장 완료");
		message.setData(result);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
