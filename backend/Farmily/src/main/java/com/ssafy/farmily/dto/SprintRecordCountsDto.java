package com.ssafy.farmily.dto;

import java.util.List;

import com.ssafy.farmily.type.RecordType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SprintRecordCountsDto {
	private Integer daily;
	private Integer challenge;
	private Integer event;

	/**
	 * 집계 함수가 포함된 JPQL에서 사용
	 */
	public interface Tuple {
		RecordType getType();
		int getCount();
	}

	public static SprintRecordCountsDto from(List<Tuple> tuples) {
		SprintRecordCountsDto dto = new SprintRecordCountsDto(0, 0, 0);
		for (Tuple tuple: tuples) {
			int count = tuple.getCount();
			switch (tuple.getType()) {
				case DAILY		-> dto.setDaily(count);
				case CHALLENGE	-> dto.setChallenge(count);
				case EVENT		-> dto.setEvent(count);
			}
		}

		return dto;
	}
}
