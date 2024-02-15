package com.ssafy.farmily.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class TimeUtils {
	public static LocalDateTime epochMillisToLocalDateTime(long epochTimeInMillis) {
		return LocalDateTime.ofInstant(
			Instant.ofEpochMilli(epochTimeInMillis),
			TimeZone.getDefault().toZoneId()
		);
	}
}
