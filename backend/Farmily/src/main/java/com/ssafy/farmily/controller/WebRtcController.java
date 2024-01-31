package com.ssafy.farmily.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.service.webrtc.WebRtcService;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.SessionProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/webrtc")
@RequiredArgsConstructor
@Tag(name = "WebRTC", description = "화상회의 API")
public class WebRtcController {

	private final WebRtcService webRtcService;

	@PostMapping("/{familyId}")
	@Operation(
		summary = "가족회의 연결",
		description = "가족회의 세션을 얻습니다. 가족회의가 없었다면 새로 만듭니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "세션 연결 성공",
			content = @Content(
				schema = @Schema(
					implementation = String.class,
					description = "세션에 접속할 수 있는 WebSocket URL"
				)
			)
		)
	})
	public ResponseEntity<String> post(
		@AuthenticationPrincipal String username,
		@PathVariable Long familyId
	) {
		String urlWithToken = webRtcService.enterConference(username, familyId);
		return ResponseEntity.ok(urlWithToken);
	}

}
