package com.ssafy.farmily.service.webrtc;

import com.ssafy.farmily.dto.OpenViduWebhookEventDto;
import com.ssafy.farmily.entity.Conference;
import com.ssafy.farmily.dto.ConferenceJoinResponseDto;

public interface WebRtcService {
	ConferenceJoinResponseDto enterConference(String username, Long familyId);

	Conference createConference(Long familyId);

	String joinConference(String username, Conference conference);

	void onSessionCreated(OpenViduWebhookEventDto.SessionCreated dto);

	void onSessionDestroyed(OpenViduWebhookEventDto.SessionDestroyed dto);

	void onParticipantJoined(OpenViduWebhookEventDto.ParticipantJoined dto);

	void onParticipantLeft(OpenViduWebhookEventDto.ParticipantLeft dto);
}
