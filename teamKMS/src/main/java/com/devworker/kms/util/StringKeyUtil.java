package com.devworker.kms.util;

import java.util.Random;

public class StringKeyUtil {

	public static String generateUniqueKey() {
		return getTimeStamp() + makeRandomString(10);
	}

	private static String getTimeStamp() {
		return System.currentTimeMillis() + "";
	}

	private static String makeRandomString(int length) {

		int leftLimit = 97;
		int rightLimit = 122;
		int targetStringLength = length;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);

		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}

		return buffer.toString();
	}

}
