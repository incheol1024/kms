package com.devworker.kms.service.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.devworker.kms.service.FileHandler;
import com.devworker.kms.service.FileHandlerImplAmazonS3;


@SpringBootTest
@RunWith(value = SpringJUnit4ClassRunner.class)
public class FileHandlerImplAmazonS3Test {

	@Autowired
	@Qualifier("fileHandlerImplAmazonS3")
	FileHandler fileHandler;
	
	
	File testFile;
	String downloadKey;
	
	@Before
	public void setUp() {
		
		testFile = new File("D:/app/test.png");
		downloadKey =  "155660208325552f7b0d8-dfda-4589-996e-199c13978d69";
		
	}
	
	@Test
	public void notNullBeanTest() {
		assertThat(fileHandler).isNotNull().isExactlyInstanceOf(FileHandlerImplAmazonS3.class);
	}
	
	@Test
	public void uploadFileTest() {
		String retStr = fileHandler.uploadFile(testFile);
		assertThat(retStr).isNotNull().doesNotContainOnlyWhitespaces().hasSize(23);
	}
	
	@Test
	public void downloadFileTest() {
	
		File getFile = fileHandler.downloadFile(downloadKey);
		
		assertThat(getFile.isFile()).isTrue();
		assertThat(getFile).canRead().isFile();
		
	}
	
}
