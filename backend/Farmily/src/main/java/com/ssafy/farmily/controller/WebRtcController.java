package com.ssafy.farmily.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.service.webrtc.WebRtcService;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.SessionProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WebRtcController {

	private final WebRtcService webRtcService;

	@PostMapping("/api/sessions")
	@Operation(
		summary = "WebRTC 세션 초기화",
		description = "WebRTC 세션을 초기화합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "세션 초기화 성공",
			content = @Content(
				schema = @Schema(
					implementation = String.class,
					description = "생성된 세션 ID"
				)
			)
		)
	})
	public ResponseEntity<String> initializeSession(
		@RequestBody(required = false) Map<String, Object> params
	) {
		SessionProperties properties = SessionProperties.fromJson(params).build();

		String sessionId = webRtcService.initializeSession(properties);

		return ResponseEntity.ok(sessionId);
	}

	@PostMapping("/api/sessions/{sessionId}/connections")
	@Operation(
		summary = "WebRTC 세션 연결",
		description = "WebRTC 세션에 연결합니다."
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
	public ResponseEntity<String> createConnection(
		@PathVariable String sessionId,
		@RequestBody(required = false) Map<String, Object> params
	) {
		ConnectionProperties properties = ConnectionProperties.fromJson(params).build();

		String urlWithToken = webRtcService.createConnection(sessionId, properties);

		return ResponseEntity.ok(urlWithToken);
	}
}
