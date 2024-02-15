package com.ssafy.farmily.service.webrtc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.ConferenceJoinResponseDto;
import com.ssafy.farmily.dto.OpenViduWebhookEventDto;
import com.ssafy.farmily.entity.Conference;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.ConferenceRepository;
import com.ssafy.farmily.service.family.FamilyService;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OpenViduWebRtcService implements WebRtcService {
	private final ConferenceRepository conferenceRepository;

	private final FamilyService familyService;

	private final OpenVidu openVidu;

	private static final String SESSION_ID_PREFIX = "ses_";

	@Autowired
	public OpenViduWebRtcService(
		final ConferenceRepository conferenceRepository,
		final FamilyService familyService,
		@Value("${openvidu.url}") final String url,
		@Value("${openvidu.secret}") final String secret
	) {
		this.conferenceRepository = conferenceRepository;
		this.familyService = familyService;
		this.openVidu = new OpenVidu(url, secret);
	}

	@Override
	public ConferenceJoinResponseDto enterConference(String username, Long familyId) {
		familyService.assertMembership(familyId, username);

		log.info("가족회의 접속 시도");

		Conference conference = conferenceRepository.findById(familyId)
			.orElseGet(() -> createConference(familyId));

		log.info("- Conference ID: %s".formatted(conference.getId()));
		log.info("- Conference Session ID: %s".formatted(conference.getSessionId()));

		String token = joinConference(username, conference);

		log.info("- Token: %s".formatted(token));

		return ConferenceJoinResponseDto.builder()
			.sessionUrl(token)
			.build();
	}

	@Override
	public Conference createConference(Long familyId) {
		log.info("가족회의 생성");

		String sessionId = SESSION_ID_PREFIX + familyId;

		SessionProperties properties = new SessionProperties.Builder()
			.customSessionId(sessionId)
			.build();

		try {
			openVidu.createSession(properties);

			Conference conference = Conference.builder()
				.id(familyId)
				.sessionId(sessionId)
				.build();

			log.info("- Conference ID: %s".formatted(conference.getId()));
			log.info("- Conference Session ID: %s".formatted(conference.getSessionId()));

			conferenceRepository.save(conference);

			return conference;
		} catch (OpenViduException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String joinConference(String username, Conference conference) {
		log.info("가족회의 접속");
		log.info("- Username: %s".formatted(username));
		log.info("- Conference Session ID: %s".formatted(conference.getSessionId()));

		ConnectionProperties properties = new ConnectionProperties.Builder()
			// .data(username)		// TODO: Front 측에서 필요한 데이터 jsonify 후 넘겨주기
			.build();

		try {
			Session session = openVidu.getActiveSessions().stream()
				.filter(s -> conference.getSessionId().equals(s.getSessionId()))
				.findAny()
				.orElseThrow(() -> new NoSuchContentException("해당 세션을 찾을 수 없습니다."));

			Connection connection = session.createConnection(properties);

			return connection.getToken();

		} catch (OpenViduException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void onSessionCreated(OpenViduWebhookEventDto.SessionCreated dto) {
		// do nothing
	}

	@Override
	public void onSessionDestroyed(OpenViduWebhookEventDto.SessionDestroyed dto) {
		log.info("가족회의 삭제됨");
		log.info("- 삭제 이유: %s".formatted(dto.getReason().toString()));

		String sessionId = dto.getSessionId();
		Long familyId = Long.parseLong(sessionId.substring(SESSION_ID_PREFIX.length()));

		conferenceRepository.deleteById(familyId);
	}

	@Override
	public void onParticipantJoined(OpenViduWebhookEventDto.ParticipantJoined dto) {
		// do nothing
	}

	@Override
	public void onParticipantLeft(OpenViduWebhookEventDto.ParticipantLeft dto) {
		// do nothing
	}
}
