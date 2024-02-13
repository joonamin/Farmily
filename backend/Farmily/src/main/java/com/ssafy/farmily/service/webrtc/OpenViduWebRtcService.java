package com.ssafy.farmily.service.webrtc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.farmily.dto.OpenViduWebhookEventDto;
import com.ssafy.farmily.entity.Conference;
import com.ssafy.farmily.dto.ConferenceJoinResponseDto;
import com.ssafy.farmily.entity.Member;
import com.ssafy.farmily.exception.NoSuchContentException;
import com.ssafy.farmily.repository.ConferenceRepository;
import com.ssafy.farmily.service.family.FamilyService;
import com.ssafy.farmily.service.member.MemberService;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;

@Service
public class OpenViduWebRtcService implements WebRtcService {
	private final ConferenceRepository conferenceRepository;

	private final FamilyService familyService;

	private final OpenVidu openVidu;

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

		Conference conference = conferenceRepository.findById(familyId)
			.orElseGet(() -> createConference(familyId));

		String token = joinConference(username, conference);

		return ConferenceJoinResponseDto.builder()
			.sessionUrl(token)
			.build();
	}

	@Override
	public Conference createConference(Long familyId) {
		String sessionId = "ses_" + familyId;

		SessionProperties properties = new SessionProperties.Builder()
			.customSessionId(sessionId)
			.build();

		try {
			openVidu.createSession(properties);

			Conference conference = Conference.builder()
				.id(familyId)
				.sessionId(sessionId)
				.build();

			conferenceRepository.save(conference);

			return conference;
		} catch (OpenViduException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String joinConference(String username, Conference conference) {
		ConnectionProperties properties = new ConnectionProperties.Builder()
			// .data(username)		// TODO: Front 측에서 필요한 데이터 jsonify 후 넘겨주기
			.build();

		try {
			Session session = openVidu.getActiveSession(conference.getSessionId());
			if (session == null)
				throw new NoSuchContentException("해당 세션을 찾을 수 없습니다.");

			Connection connection = session.createConnection(properties);

			return connection.getToken();

		} catch (OpenViduException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void onSessionCreated(OpenViduWebhookEventDto.SessionCreated dto) {

	}

	@Override
	public void onSessionDestroyed(OpenViduWebhookEventDto.SessionDestroyed dto) {

	}

	@Override
	public void onParticipantJoined(OpenViduWebhookEventDto.ParticipantJoined dto) {

	}

	@Override
	public void onParticipantLeft(OpenViduWebhookEventDto.ParticipantLeft dto) {

	}
}
