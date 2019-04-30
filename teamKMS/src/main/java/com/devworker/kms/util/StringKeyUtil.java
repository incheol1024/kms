package com.devworker.kms.util;

import org.apache.commons.lang3.RandomStringUtils;

public class StringKeyUtil {

	public static String generateUniqueKey() {
		return getTimeStamp() + makeRandomString(10);
	}

	private static String makeRandomString(int length) {
		return RandomStringUtils.random(length, true, true);
	}

	private static String getTimeStamp() {
		return System.currentTimeMillis() + "";
	}

}
