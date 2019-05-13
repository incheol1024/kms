package com.devworker.kms.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class StringKeyUtilTest {

	@Test
	public void generateUniqueKeyTest() {
		int testSize = 10000;
		for (int i = 0; i < testSize; i++) {
			String key = StringKeyUtil.generateUniqueKey();
			assertThat(key).doesNotContainAnyWhitespaces().hasSize(23);
		}
	}

	@Test
	public void isUniqueTest() {
		int testSize = 10000;
		Set<String> keySet = new HashSet<>(testSize);
		for (int i = 0; i < testSize; i++) {
			String key = StringKeyUtil.generateUniqueKey();
			assertThat(keySet.contains(key)).isFalse();
			keySet.add(key);
		}
	}
	
	@Test
	public void whatIsShapeOfKey() {
			System.out.println(StringKeyUtil.generateUniqueKey());
	}
	
	@Test
	public void systemCurrentMillsSpeedTest() {
		
		long start = System.currentTimeMillis();
		for(int i = 0; i < 100000000; i++) {
			System.currentTimeMillis();
		}
		System.out.println("System.currentTimeMills() = " + String.valueOf(System.currentTimeMillis() - start));
	}
	
	@Test
	public void dateObjectSpeedTest() {
		
		long start = System.currentTimeMillis();
		for(int i=0; i<100000000; i++) {
			Date date = new Date();
		}
		
		System.out.println("new Date() = " + String.valueOf(System.currentTimeMillis() - start));
		
	}

}
