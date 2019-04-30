package com.devworker.kms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PropertyTest {

	@Value(value = "${amazon.s3.bucket}")
	String bucket;
	
	@Test
	public void test() {
		
		System.out.println(bucket);
		assertThat(bucket).isNotNull();
	}

}
