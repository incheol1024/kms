package com.devworker.kms.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileUtilTest {

	MultipartFile multipartFile;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		multipartFile = new MockMultipartFile("test.png", new FileInputStream(new File("D:/app/test.png")));
	}
	
	@Test
	public void convertToFileTest() throws IOException {
		File newFile  = FileUtil.convertToFile(multipartFile, "D:/app");
		assertThat(newFile).canRead().isFile().exists().isExactlyInstanceOf(File.class);
	}
}
