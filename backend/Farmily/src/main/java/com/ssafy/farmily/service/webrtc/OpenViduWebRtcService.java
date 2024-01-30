package com.ssafy.farmily.service.webrtc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.exception.NoSuchContentException;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import lombok.SneakyThrows;

@Service
public class OpenViduWebRtcService implements WebRtcService {
	private final OpenVidu openVidu;

	@Autowired
	public OpenViduWebRtcService(
		@Value("${openvidu.url}") final String url,
		@Value("${openvidu.secret}") final String secret
	) {
		this.openVidu = new OpenVidu(url, secret);
	}

	public String initializeSession(SessionProperties sessionProperties) {
		try {
			Session session = openVidu.createSession(sessionProperties);
			return session.getSessionId();
		} catch (OpenViduException ex) {
			throw new RuntimeException(ex);
		}
	}

	public String createConnection(String sessionId, ConnectionProperties connectionProperties) {
		try {
			Session session = openVidu.getActiveSession(sessionId);
			if (session == null)
				throw new NoSuchContentException("해당 세션을 찾을 수 없습니다.");

			Connection connection = session.getConnection(sessionId);
			return connection.getToken();
		} catch (OpenViduException ex) {
			throw new RuntimeException(ex);
		}
	}
}
