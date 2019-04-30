package com.devworker.kms.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class StringKeyUtilTest {

	@Test
	public void generateUniqueKeyTest() {
		int testSize = 1000000;
		for (int i = 0; i < testSize; i++) {
			String key = StringKeyUtil.generateUniqueKey();
			assertThat(key).doesNotContainAnyWhitespaces().hasSize(23);
		}
	}

	@Test
	public void isUniqueTest() {
		int testSize = 1000000;
		Set<String> keySet = new HashSet<>(testSize);
		for (int i = 0; i < testSize; i++) {
			String key = StringKeyUtil.generateUniqueKey();
			assertThat(keySet.contains(key)).isFalse();
			keySet.add(key);
		}
	}

}
