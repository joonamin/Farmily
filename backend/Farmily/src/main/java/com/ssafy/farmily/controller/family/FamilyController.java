package com.ssafy.farmily.controller.family;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.common.Message;
import com.ssafy.farmily.common.StatusEnum;
import com.ssafy.farmily.dto.FamilyItemDTO;
import com.ssafy.farmily.dto.FamilyMainDTO;
import com.ssafy.farmily.entity.Family;
import com.ssafy.farmily.exception.NotFoundFamilyId;
import com.ssafy.farmily.service.FamilyService;

@RestController
@RequestMapping("/family")
public class FamilyController {
	@Autowired
	FamilyService familyService;


	// 요청 시 메인 인덱스에 DB에 저장된 가족정보를 가져옴
	@GetMapping("/{familyId}")
	public ResponseEntity<Message> mainIndex(@PathVariable Long familyId){
		Message message = new Message();
		HttpHeaders headers =new HttpHeaders();
		ResponseEntity<Message> responseEntity = null;
		try {
			FamilyMainDTO familyMainDTO = familyService.familyMain(familyId);
			message.setStatus(StatusEnum.OK);
			message.setMessage("가족 메인 정보");
			message.setData(familyMainDTO);
			responseEntity = new ResponseEntity<>(message,headers,HttpStatus.OK);

		} catch (NotFoundFamilyId e){
			message = e.errorMessage();
			responseEntity = new ResponseEntity<>(message,headers,HttpStatus.NOT_FOUND);
		}

		return responseEntity;

	}

	@GetMapping("/{familyId}/inventory")
	public ResponseEntity<Message> inventory(@PathVariable Long familyId){
		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Message> responseEntity = null;
		try {
			List<FamilyItemDTO> familyItemDTOList = familyService.familyInventory(familyId);
			message.setStatus(StatusEnum.OK);
			message.setMessage("인벤토리 정보");
			message.setData(familyItemDTOList);
			responseEntity = new ResponseEntity<>(message,headers,HttpStatus.OK);

		} catch (NotFoundFamilyId e){
			message = e.errorMessage();
			responseEntity = new ResponseEntity<>(message,headers,HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
}
