package com.ssafy.farmily.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.farmily.service.webrtc.OpenViduWebRtcService;
import com.ssafy.farmily.service.webrtc.WebRtcService;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.SessionProperties;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WebRtcController {

	private final WebRtcService webRtcService;

	@PostMapping("/api/sessions")
	public ResponseEntity<String> initializeSession(
		@RequestBody(required = false) Map<String, Object> params)
	{
		SessionProperties properties = SessionProperties.fromJson(params).build();

		String sessionId = webRtcService.initializeSession(properties);

		return ResponseEntity.ok(sessionId);
	}

	@PostMapping("/api/sessions/{sessionId}/connections")
	public ResponseEntity<String> createConnection(
		@PathVariable("sessionId") String sessionId,
		@RequestBody(required = false) Map<String, Object> params)
	{
		ConnectionProperties properties = ConnectionProperties.fromJson(params).build();

		String urlWithToken = webRtcService.createConnection(sessionId, properties);

		return ResponseEntity.ok(urlWithToken);
	}
}
