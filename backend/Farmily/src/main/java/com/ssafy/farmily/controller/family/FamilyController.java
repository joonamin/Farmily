package com.ssafy.farmily.controller.family;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.dto.FamilyMainDTO;
import com.ssafy.farmily.service.FamilyService;

@RestController
@RequestMapping("/family")
public class FamilyController {
	@Autowired
	FamilyService familyService;
	@GetMapping("/{familyId}")
	public FamilyMainDTO mainIndex(@PathVariable Long familyId){
		return familyService.familyMain(familyId);

	}
}
