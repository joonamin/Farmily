package com.ssafy.farmily.dto;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;

import com.ssafy.farmily.utils.TimeUtils;

import lombok.Getter;

@Getter
public abstract class OpenViduWebhookEventDto {

	private final EventType eventType;
	private final LocalDateTime timestamp;

	public enum EventType {
		SESSION_CREATED,
		SESSION_DESTROYED,
		PARTICIPANT_JOINED,
		PARTICIPANT_LEFT
	}

	private OpenViduWebhookEventDto(Map<String, Object> map) {
		String mapEvent = (String) map.get("event");
		this.eventType = EnumUtils.getEnumIgnoreCase(EventType.class, mapEvent);

		Long mapTimestamp = (Long) map.get("timestamp");
		this.timestamp = TimeUtils.epochMillisToLocalDateTime(mapTimestamp);
	}

	@Getter
	public static class SessionCreated extends OpenViduWebhookEventDto {
		private final String sessionId;

		public SessionCreated(Map<String, Object> map) {
			super(map);
			this.sessionId = (String) map.get("sessionId");
		}
	}

	@Getter
	public static class SessionDestroyed extends OpenViduWebhookEventDto {
		private final String sessionId;
		private final LocalDateTime startTime;
		private final Duration duration;
		private final Reason reason;

		public enum Reason {
			LAST_PARTICIPANT_LEFT,
			SESSION_CLOSED_BY_SERVER,
			MEDIA_SERVER_DISCONNECT,
			NODE_CRASHED,
			OPENVIDU_SERVER_STOPPED,
			AUTOMATIC_STOP
		}

		public SessionDestroyed(Map<String, Object> map) {
			super(map);

			this.sessionId = (String) map.get("sessionId");

			Long mapStartTime = (Long) map.get("startTime");
			this.startTime = TimeUtils.epochMillisToLocalDateTime(mapStartTime);

			Long mapDuration = (Long) map.get("duration");
			this.duration = Duration.ofSeconds(mapDuration);

			String mapReason = (String) map.get("reason");
			this.reason = EnumUtils.getEnumIgnoreCase(Reason.class, mapReason);
		}
	}

	private static abstract class ParticipantEvent extends OpenViduWebhookEventDto {
		private final String sessionId;
		private final String connectionId;
		private final InetAddress ip;
		private final String platform;
		private final String clientData;	// TODO: type 합의
		private final String serverData;	// TODO: type 합의

		private ParticipantEvent(Map<String, Object> map) {
			super(map);

			this.sessionId = (String) map.get("sessionId");
			this.connectionId = (String) map.get("connectionId");

			String mapIp = (String) map.get("ip");
			try {
				this.ip = InetAddress.getByName(mapIp);
			} catch (UnknownHostException ex) {
				throw new RuntimeException(ex);
			}

			this.platform = (String) map.get("platform");
			this.clientData = (String) map.get("clientData");
			this.serverData = (String) map.get("serverData");
		}
	}

	@Getter
	public static class ParticipantJoined extends ParticipantEvent {
		public ParticipantJoined(Map<String, Object> map) {
			super(map);
		}
	}

	@Getter
	public static class ParticipantLeft extends ParticipantEvent {
		private LocalDateTime startTime;
		private Duration duration;
		private Reason reason;

		public enum Reason {
			DISCONNECT,
			FORCE_DISCONNECT_BY_USER,
			FORCE_DISCONNECT_BY_SERVER,
			SESSION_CLOSED_BY_SERVER,
			NETWORK_DISCONNECT,
			MEDIA_SERVER_DISCONNECT,
			NODE_CRASHED,
			OPENVIDU_SERVER_STOPPED
		}

		public ParticipantLeft(Map<String, Object> map) {
			super(map);

			Long mapStartTime = (Long)map.get("startTime");
			this.startTime = TimeUtils.epochMillisToLocalDateTime(mapStartTime);

			Long mapDuration = (Long)map.get("duration");
			this.duration = Duration.ofSeconds(mapDuration);

			String mapReason = (String)map.get("reason");
			this.reason = EnumUtils.getEnumIgnoreCase(Reason.class, mapReason);
		}
	}
}
