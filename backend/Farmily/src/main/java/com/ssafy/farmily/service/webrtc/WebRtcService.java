package com.ssafy.farmily.service.webrtc;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.SessionProperties;

public interface WebRtcService {
	String initializeSession(SessionProperties sessionProperties);
	String createConnection(String sessionId, ConnectionProperties connectionProperties);
}
