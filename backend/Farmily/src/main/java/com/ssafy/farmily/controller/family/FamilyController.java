package com.ssafy.farmily.controller.family;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.FamilyBasketDto;
import com.ssafy.farmily.dto.FamilyItemDto;
import com.ssafy.farmily.dto.FamilyMainDto;
import com.ssafy.farmily.dto.PlacingItemRequestDto;
import com.ssafy.farmily.service.family.FamilyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/family")
@Slf4j
@RequiredArgsConstructor
public class FamilyController {
	private final FamilyService familyService;

	// 그래서 준비했다, lombok!!!

	// 요청 시 메인 인덱스에 DB에 저장된 가족정보를 가져옴

	@GetMapping("/{familyId}")
	@Operation(
		summary = "가족 메인 정보 조회",
		description = "메인에서 가족의 정보를 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "조회 성공",
			content = @Content(schema = @Schema(implementation = FamilyMainDto.class))
		)
	})
	public ResponseEntity<FamilyMainDto> mainIndex(@PathVariable Long familyId) {
		FamilyMainDto familyMainDTO = familyService.setMainFamilyInfo(familyId);

		return ResponseEntity.ok(familyMainDTO);
	}

	@GetMapping("/{familyId}/inventory")
	@Operation(
		summary = "인벤토리 조회",
		description = "가족의 인벤토리를 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "조회 성공",
			content = @Content(schema = @Schema(implementation = FamilyItemDto.class))
		)
	})
	public ResponseEntity<List<FamilyItemDto>> getInventory(@PathVariable Long familyId) {
		List<FamilyItemDto> familyItemDtoList = familyService.getFamilyInventory(familyId);

		return ResponseEntity.ok(familyItemDtoList);
	}

	@GetMapping("/{familyId}/basket")
	@Operation(
		summary = "스프린트 조회",
		description = "가족의 스프린트를 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "조회 성공",
			content = @Content(schema = @Schema(implementation = FamilyBasketDto.class))
		)
	})
	public ResponseEntity<List<FamilyBasketDto>> getFamilyBasketList(@PathVariable Long familyId) {
		List<FamilyBasketDto> familyBasketDTOList = familyService.getFamilySprintList(familyId);

		return ResponseEntity.ok(familyBasketDTOList);
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
	@PostMapping("/placement")
	@Operation(
		summary = "아이템 배치",
		description = "DB에 저장된 배치 배열을 제거하고 새로운 배열을 저장합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "배열 저장 성공"
		)
	})
	public ResponseEntity<Void> itemPlacement(@RequestBody PlacingItemRequestDto placementList) {
		familyService.placingItems(placementList);

		return ResponseEntity.ok().build();
	}
}
