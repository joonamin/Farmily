package com.ssafy.farmily.utils;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class RandomNumberGenerator {

	private static final Random random = new Random();

	public static Set<Long> getRandomUniqueLongs(long startInclusive, long endExclusive, long count) {
		long rangeSize = endExclusive - startInclusive;

		if (count > rangeSize) {
			throw new RuntimeException("뽑으려는 수의 개수보다 범위의 크기가 더 작습니다.");
		}

		return random.longs(startInclusive, endExclusive)
			.distinct()
			.limit(count)
			.boxed()
			.collect(Collectors.toSet());
	}
}
