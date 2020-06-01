package com.blink.util;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import com.blink.enumeration.SearchPeriod;

public class CommonUtils {

	public static int createRandomNumber(int length) {
		int range = (int) Math.pow(10, length);
		int trim = (int) Math.pow(10, length - 1);

		int result = ThreadLocalRandom.current().nextInt(range) + trim;

		if (result > range) {
			result = result - trim;
		}

		return result;
	}

	public static LocalDateTime getSearchPeriod(SearchPeriod period) {
		LocalDateTime time = LocalDateTime.now();

		switch (period) {
		case ONEDAY:
			time = time.minusDays(1);
			break;
		case ONEWEEK:
			time = time.minusDays(7);
			break;
		case ONEMONTH:
			time = time.minusDays(30);
			break;
		case THREEMONTH:
			time = time.minusDays(90);
			break;
		case SIXMONTH:
			time = time.minusDays(180);
			break;
		case ONEYEAR:
			time = time.minusDays(365);
			break;
		}

		return time;
	}

	public static String getRamdomPassword(int len) {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		int idx = 0;
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < len; i++) {

			idx = (int) (charSet.length * Math.random()); // 36 * 생성된 난수를 Int로 추출 (소숫점제거)
			sb.append(charSet[idx]);
		}

		return sb.toString();
	}
}
