package com.devworker.kms.service.board;

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

import com.devworker.kms.dto.common.FileDto;
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

	MultipartFile multipartFile;

	@Before
	public void setUp() throws FileNotFoundException, IOException {

		testFile = new File("D:/app/test.png");
		downloadKey = "155660208325552f7b0d8-dfda-4589-996e-199c13978d69";
		multipartFile = new MockMultipartFile("test.png", new FileInputStream(testFile));
	}

	@Test
	public void notNullBeanTest() {
		assertThat(fileHandler).isNotNull().isExactlyInstanceOf(FileHandlerImplAmazonS3.class);
	}

	@Test
	public void uploadFileTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		
		Method method =  fileHandler.getClass().getDeclaredMethod("uploadFile", File.class);
		method.setAccessible(Boolean.valueOf(true));
		String actual = (String) method.invoke(fileHandler, testFile);

		assertThat(actual).isNotNull().doesNotContainOnlyWhitespaces().hasSize(23);
	}

	@Test
	public void downloadFileTest() {

		File getFile = fileHandler.downloadFile(downloadKey);

		assertThat(getFile.isFile()).isTrue();
		assertThat(getFile).canRead().isFile();
	}

	@Test
	public void processUploadFileTest() throws Exception {

		FileDto expectedFileDto = new FileDto.FileDtoBuilder("??")
				.setFileExt(FilenameUtils.getExtension(testFile.getName())).setFileName(testFile.getName())
				.setFileSize(FileUtils.sizeOf(testFile)).build();

		FileDto fileDto = fileHandler.processUploadFile(multipartFile);
		assertThat(fileDto).isInstanceOf(FileDto.class).isExactlyInstanceOf(FileDto.class)
				.isEqualToComparingOnlyGivenFields(expectedFileDto, "fileSize", "fileExt");

	}
	
	@Test
	public void deleteFileTest() throws Exception {
		String key = "155660208325552f7b0d8-dfda-4589-996e-199c13978d69";
		boolean actual = fileHandler.deleteFile(key);
		
		assertThat(actual).isTrue();
		
		key = "asdfasdfasdfasdfasdfasdfsadf";
		actual = fileHandler.deleteFile(key);
		
		assertThat(actual).isFalse();
		
	}

}
