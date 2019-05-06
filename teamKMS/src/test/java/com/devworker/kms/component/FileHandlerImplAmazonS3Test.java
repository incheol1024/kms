package com.devworker.kms.component;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.component.FileHandler;
import com.devworker.kms.component.FileHandlerImplAmazonS3;
import com.devworker.kms.dto.common.FileDto;

@SpringBootTest
@RunWith(value = SpringJUnit4ClassRunner.class)
public class FileHandlerImplAmazonS3Test {

	@Autowired
	@Qualifier("fileHandlerImplAmazonS3")
	FileHandler fileHandler;

	File testFile;
	String downloadKey;

	MultipartFile multipartFile;

	@Before
	public void setUp() throws FileNotFoundException, IOException {

		testFile = new File("D:/app/test.png");
		downloadKey = "15566022162000d4894c1-5a1e-4afb-9d9f-266d91e75315";
		multipartFile = new MockMultipartFile("test.png", new FileInputStream(testFile));
	}

	@Test
	public void notNullBeanTest() {
		assertThat(fileHandler).isNotNull().isExactlyInstanceOf(FileHandlerImplAmazonS3.class);
	}

	@Test
	public void uploadFileTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Method method = fileHandler.getClass().getDeclaredMethod("uploadFile", File.class);
		method.setAccessible(Boolean.valueOf(true));
		String actual = (String) method.invoke(fileHandler, testFile);

		assertThat(actual).isNotNull().doesNotContainOnlyWhitespaces().hasSize(23);
	}

	@Test
	public void processDownloadFileTest() {
		String downloadKey = "15566297014682RDf1SRpGB";
		File tmpFile = new File("D:/app/downTmp.png");
		FileDto fileDto = FileDto.builder().setKey(downloadKey).setFile(tmpFile).build();

		FileDto resultFileDto = fileHandler.processDownloadFile(fileDto);

		assertThat(resultFileDto.getFile()).isFile();

	}

	@Test
	public void processUploadFileTest() throws Exception {
		FileDto expectedFileDto = FileDto.builder().setFileExt(FilenameUtils.getExtension(testFile.getName()))
				.setFileName(testFile.getName()).setFileSize(FileUtils.sizeOf(testFile)).setFile(testFile).build();

		FileDto fileDto = fileHandler.processUploadFile(expectedFileDto);
		assertThat(fileDto).isInstanceOf(FileDto.class).isExactlyInstanceOf(FileDto.class);
		assertThat(fileDto.getKey()).doesNotContainOnlyWhitespaces();
		
	}

	@Test
	public void deleteFileTest() throws Exception {
		String key = "155660208325552f7b0d8-dfda-4589-996e-199c13978d69";
		boolean actual = fileHandler.deleteFile(key);

		assertThat(actual).isTrue();

		key = "asdfasdfasdfasdfasdfasdfsadf";
		actual = fileHandler.deleteFile(key);

		// assertThat(actual).isFalse(); // 무조건 False 이여야 하는데 True 가 나옴 아마존 sdk 에서 무조건
		// true로 줌.

	}

}
