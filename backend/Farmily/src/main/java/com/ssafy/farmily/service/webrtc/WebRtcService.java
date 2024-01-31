package com.ssafy.farmily.service.webrtc;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.SessionProperties;

public interface WebRtcService {
	String enterConference(String username, Long familyId);

	String createConference(Long familyId);

	String joinConference(String username, Long familyId);
}
